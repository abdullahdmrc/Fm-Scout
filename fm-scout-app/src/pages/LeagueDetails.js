import React, { useState, useEffect } from "react";
import { useParams, useNavigate } from "react-router-dom";
import axios from "axios";
import "./LeagueDetails.css";

const LeagueDetails = () => {
  const { leagueId } = useParams(); // URL'den leagueId alınır
  const [league, setLeague] = useState(null);
  const [teams, setTeams] = useState([]);
  const navigate = useNavigate();

  useEffect(() => {
    // Lig bilgilerini getirme
    const fetchLeagueDetails = async () => {
      try {
        const leagueResponse = await axios.get(
          `http://localhost:8080/api/v1/leagues/${leagueId}`
        );
        setLeague(leagueResponse.data);

        const teamsResponse = await axios.get(
          `http://localhost:8080/api/v1/leagues/${leagueId}/clubs`
        );
        setTeams(teamsResponse.data);
      } catch (error) {
        console.error("Error fetching league details:", error);
      }
    };

    fetchLeagueDetails();
  }, [leagueId]);

  if (!league) {
    return <div>Loading league details...</div>;
  }

  return (
    <div className="league-details">
      {/* Sol tarafta lig bilgileri */}
      <div className="league-info">
        <h2 style={{color:"black"}}>{league.leagueName}</h2>
        <p><strong>Tier:</strong> {league.leagueTier}</p>
        <p><strong>Country:</strong> {league.countryName}</p>
      </div>

      {/* Sağ tarafta takımlar */}
      <div className="league-teams">
        <h3>Teams in {league.leagueName}</h3>
        <table className="teams-table">
          <thead>
            <tr>
              <th>Team Name</th>
              <th>Manager</th>
            </tr>
          </thead>
          <tbody>
            {teams.map((club) => (
              <tr
                key={club.clubId}
                className="team-row"
                onClick={() => navigate(`/clubs/${club.clubId}`)}
              >
                <td>{club.clubName}</td>
                <td>{club.managerName}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default LeagueDetails;
