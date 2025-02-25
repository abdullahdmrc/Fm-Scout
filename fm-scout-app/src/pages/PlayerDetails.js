/*import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";

const PlayerDetails = () => {
  const { player_id } = useParams(); // Player ID'yi alıyoruz
  const [player, setPlayer] = useState(null);

  useEffect(() => {
    const fetchPlayerDetails = async () => {
      const response = await fetch(`http://localhost:8080/api/v1/football-players/${player_id}`);
      const data = await response.json();
      setPlayer(data);
    };

    fetchPlayerDetails();
  }, [player_id]);

  if (!player) return <div>Loading player details...</div>;

  return (
    <div>
      <h1>{player.name}</h1>
      <p>Club: {player.clubName}</p>
      <p>Position: {player.positions}</p>
      <p>Salary: {player.salary}</p>
      <p>CA: {player.ca}</p>
      <p>PA: {player.pa}</p>
      <p>Age: {player.age}</p>
    </div>
  );
};

export default PlayerDetails;*/  // bu kod ana tasarımdan oncekı hal sadece blgılerı bastıyoruduk bu calısıyor sımdılık dursun
import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import "./PlayerDetails.css";

const PlayerDetails = () => {
  const { player_id } = useParams(); // Player ID'yi alıyoruz
  const [player, setPlayer] = useState(null);

  useEffect(() => {
    const fetchPlayerDetails = async () => {
      const response = await fetch(`http://localhost:8080/api/v1/football-players/${player_id}`);
      const data = await response.json();
      setPlayer(data);
    };

    fetchPlayerDetails();
  }, [player_id]);

  if (!player) return <div>Loading player details...</div>;
  // Yetenek kategorileri
  const technicalAttributes = [
    { name: "Corners", value: player.corners },
    { name: "Crossing", value: player.crossing },
    { name: "Dribbling", value: player.dribbling },
    { name: "Finishing", value: player.finishing },
    { name: "First Touch", value: player.firstTouch },
    { name: "Free Kick Taking", value: player.freeKickTaking },
    { name: "Heading", value: player.heading },
    { name: "Long Shots", value: player.longShots },
    { name: "Long Throws", value: player.longThrows },
    { name: "Marking", value: player.marking },
    { name: "Passing", value: player.passing },
    { name: "Penalty Taking", value: player.penaltyTaking },
    { name: "Tackling", value: player.tackling },
    { name: "Technique", value: player.technique },
  ];

  const mentalAttributes = [
    { name: "Aggression", value: player.aggression },
    { name: "Anticipation", value: player.anticipation },
    { name: "Bravery", value: player.bravery },
    { name: "Composure", value: player.composure },
    { name: "Concentration", value: player.concentration },
    { name: "Decisions", value: player.decision },
    { name: "Determination", value: player.determination },
    { name: "Flair", value: player.flair },
    { name: "Leadership", value: player.leadership },
    { name: "Off The Ball", value: player.offTheBall },
    { name: "Positioning", value: player.positioning },

    { name: "Teamwork", value: player.teamWork },
    { name: "Vision", value: player.vision },
    { name: "Work Rate", value: player.workRate },
  ];

  const physicalAttributes = [
    { name: "Acceleration", value: player.acceleration },
    { name: "Agility", value: player.agility },
    { name: "Balance", value: player.balance },
    { name: "Jumping Reach", value: player.jumpingReach },
    { name: "Natural Fitness", value: player.naturalFitness },
    { name: "Pace", value: player.pace },
    { name: "Stamina", value: player.stamina },
    { name: "Strength", value: player.strength },
    { name: "Stability", value: player.stability },
    <br></br>,
    { name: "Favourite", value: player.addToFavourites },
  ];

  // Renk skalalarını hesaplama fonksiyonları
  const getRatingColor = (value) => {
    if (value <= 119) return "#cd7f32"; // Bronz
    if (value <= 159) return "#c0c0c0"; // Gümüş
    if (value <= 179) return "#ffd700"; // Altın
    return "#9c27b0"; // Mor
  };

  const getFootColor = (value) => {
    if (value <= 10) return "red";
    if (value <= 14) return "yellow";
    return "green";
  };

  const getAttributeColor = (value) => {
    if (value <= 10) return "red";
    if (value <= 14) return "orange";
    return "green";
  };

  return (
    <div className="player-details">
      <div className="player-header">
        {/* Sol taraf: Default profil ikonu */}
        <div className="player-photo">
          <div className="default-icon">👤</div>
        </div>

        {/* Sağ taraf: CA ve PA */}
        <div className="ratings">
          <div
            className="rating-box"
            style={{ backgroundColor: getRatingColor(player.ca) }}
          >
            {player.ca}
          </div>
          <div
            className="rating-box"
            style={{ backgroundColor: getRatingColor(player.pa) }}
          >
            {player.pa}
          </div>
        </div>
      </div>

      {/* Player Info */}
      <div className="player-info">
        <h2 style={{ color: "black" }}>{player.name}</h2>
        <p>Club: {player.clubName}</p>
        <p>Country: {player.countryName}</p>
        <p>Position(s): {player.positions.join(", ")}</p>
        <p>Age: {player.age}</p>
        <p>Salary: ${player.salary.toLocaleString()}</p>
        <p>Market Value: ${player.marketValue.toLocaleString()}</p>
        <p>Rental Club: {player.rentalClubName}</p>
      </div>

      {/* Ayak Kullanımı */}
      <div className="foot-info">
        <div className="foot-box">
          <div
            className="foot-circle"
            style={{ backgroundColor: getFootColor(player.leftFoot) }}
          ></div>
          <p>Left Foot: {player.leftFoot}</p>
        </div>
        <div className="foot-box">
          <div
            className="foot-circle"
            style={{ backgroundColor: getFootColor(player.rightFoot) }}
          ></div>
          <p>Right Foot: {player.rightFoot}</p>
        </div>
      </div>
      {/* Teknik, Zihinsel ve Fiziksel Yetenekler */}
      <p
        style={{
          textAlign: "center", // Yatayda ortalama
          margin: "20px auto", // Yukarı ve aşağıda boşluk, ortalamayı destekler
         
          fontSize: "18px", // Yazı boyutunu artırır
        }}
      >
        All of our stats have been normalized 0-20
      </p>
      <div className="attributes">
        <div className="attribute-category">


          <h3>Technical</h3>
          <ul>
            {technicalAttributes.map((attr, idx) => (
              <li key={idx}>
                {attr.name}: <span style={{ color: getAttributeColor(attr.value) }}>{attr.value}</span>
              </li>
            ))}
          </ul>
        </div>
        <div className="attribute-category">
          <h3>Mental</h3>
          <ul>
            {mentalAttributes.map((attr, idx) => (
              <li key={idx}>
                {attr.name}: <span style={{ color: getAttributeColor(attr.value) }}>{attr.value}</span>
              </li>
            ))}
          </ul>
        </div>
        <div className="attribute-category">
          <h3>Physical</h3>
          <ul>
            {physicalAttributes.map((attr, idx) => (
              <li key={idx}>
                {attr.name}: <span style={{ color: getAttributeColor(attr.value) }}>{attr.value}</span>
              </li>
            ))}
            
          </ul>
        </div>
      </div>
    </div>
  );
};

export default PlayerDetails;


