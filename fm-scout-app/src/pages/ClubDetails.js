import React, { useState, useEffect } from "react";
import { useParams } from "react-router-dom";
import axios from "axios";
import "./ClubDetails.css";

const ClubDetails = () => {
  const { clubId } = useParams(); // useParams'tan id alınır
  const [club, setClub] = useState(null);
  const [players, setPlayers] = useState([]);
  const [error, setError] = useState(null);
  
  useEffect(() => {
      const fetchClubDetails = async () => {
        const response = await fetch(`http://localhost:8080/api/v1/clubs/${clubId}`);
        
        const data = await response.json();
        setClub(data);

        const playerResponse = await axios.get(
          `http://localhost:8080/api/v1/clubs/${clubId}/players`
        );
        setPlayers(playerResponse.data);
      };
  
      fetchClubDetails();
    }, [clubId]);
  

  const getReputationColor = (reputation) => {
    if (reputation >= 85) return "purple";
    if (reputation >= 70) return "blue";
    if (reputation >= 50) return "green";
    return "red";
  };

  if (error) {
    return <div>{error}</div>;
  }

  if (!club) {
    return <div>Loading club details...</div>;
  }

  return (
    <div className="club-details">
      <div className="club-info">
        <h2>{club.clubName}</h2>
        <p><strong>Foundation Date:</strong> {club.foundationDate}</p>
        <hr />
        <p><strong>League:</strong> {club.leagueName}</p>
        <hr />
        <p>
          <strong>Reputation:</strong>{" "}
          <span style={{ color: getReputationColor(club.reputation) }}>
            {club.reputation}
          </span>
        </p>
        <hr />
        <p><strong>President:</strong>{club.presidentName}</p>
        <hr/>
        <p><strong>Manager:</strong> {club.managerName}</p>
      </div>

      <div className="club-players">
        <h3>Players</h3>
        <table className="players-table">
          <thead>
            <tr>
              <th>Name</th>
              <th>Position</th>
              <th>Age</th>
              <th>Salary</th>
            </tr>
          </thead>
          <tbody>
            {players.map((player) => (
              <tr key={player.id}>
                <td>{player.name}</td>
                <td>{Array.isArray(player.positions) ? player.positions.join(", ") : player.positions}</td>
                <td>{player.age}</td>
                <td>{player.salary}</td>
              </tr>
            ))}
          </tbody>
        </table>
      </div>
    </div>
  );
};

export default ClubDetails;



