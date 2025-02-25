// src/context/PlayersContext.js
import React, { createContext, useState, useContext, useEffect } from "react";
import axios from "axios";

// Context oluşturuluyor
const PlayersContext = createContext();

// Context'i sağlayan bileşen
export const PlayersProvider = ({ children }) => {
  const [players, setPlayers] = useState([]);
  const [loading, setLoading] = useState(true);

  useEffect(() => {
    const fetchPlayers = async () => {
      if (players.length === 0) {
        try {
          setLoading(true);
          const response = await axios.get("http://localhost:8080/api/v1/football-players");
          const transformedData = response.data.map((player) => ({
            id: player.player_id,
            name: player.name,
            club: player.clubName,
            position: player.positions,
            salary: player.salary,
            ca: player.ca,
            pa: player.pa,
            age: player.age,
          }));
          setPlayers(transformedData);
          setLoading(false);
        } catch (error) {
          console.error("Error fetching players:", error);
          setLoading(false);
        }
      }
    };

    fetchPlayers();
  }, [players.length]);

  return (
    <PlayersContext.Provider value={{ players, loading }}>
      {children}
    </PlayersContext.Provider>
  );
};

// Context'e erişmek için kullanılan hook
export const usePlayers = () => useContext(PlayersContext);
