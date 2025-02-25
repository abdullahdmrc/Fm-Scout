import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import "./Players.css";
import axios from "axios";
import { useNavigate } from "react-router-dom";

const Players = () => {
  const [players, setPlayers] = useState([]); // Tüm oyuncular
  const [currentPage, setCurrentPage] = useState(1); // Geçerli sayfa
  const [loading, setLoading] = useState(true); // Yüklenme durumu
  const [favorites, setFavorites] = useState([]); // Favoriler

  const [filters, setFilters] = useState({
    name: "",
    age: "",
    ca: "",
    pa: "",
    position: [],
  });

  const navigate = useNavigate();

  const playersPerPage = 30; // Her sayfada 30 oyuncu gösterilecek
  const paginationGroupSize = 10; // Pagination grubu

  const taken = localStorage.getItem("userId");

  useEffect(() => {
    const fetchPlayers = async () => {
      if (players.length === 0) {
        try {
          setLoading(true);
          const response = await axios.get(
            "http://localhost:8080/api/v1/football-players/free-transfers"
          );

          const transformedData = response.data.map((player) => ({
            id: player.playerId,
            name: player.name,
            club: player.clubName,
            position: player.positions,
            salary: player.salary,
            ca: player.ca,
            pa: player.pa,
            age: player.age,
            clubId: player.clubId,
          }));

          setPlayers(transformedData); // Veriyi kaydediyoruz
          setLoading(false);
        } catch (error) {
          console.error("Error fetching players:", error);
          setLoading(false);
        }
      }
    };

    fetchPlayers();
  }, [players.length]);

  // Filtreleme işlemi
  const filteredPlayers = players.filter((player) => {
    const { name, age, ca, pa, position } = filters;

    const matchesName = name
      ? player.name.toLowerCase().includes(name.toLowerCase())
      : true;
    
    const matchesAge = age ? player.age.toString() === age : true;
    const matchesCA = ca ? player.ca >= parseInt(ca, 10) : true;
    const matchesPA = pa ? player.pa >= parseInt(pa, 10) : true;
    const matchesPosition = position.length
      ? position.some((pos) => player.position.includes(pos))
      : true;

    return matchesName && matchesAge && matchesCA && matchesPA && matchesPosition;
  });

  const indexOfLastPlayer = currentPage * playersPerPage;
  const indexOfFirstPlayer = indexOfLastPlayer - playersPerPage;
  const currentPlayers = filteredPlayers.slice(indexOfFirstPlayer, indexOfLastPlayer);

  const handleFilterChange = (e) => {
    const { name, value } = e.target;

    if (name === "position") {
      const positions = [...filters.position];
      if (positions.includes(value)) {
        setFilters({
          ...filters,
          position: positions.filter((pos) => pos !== value),
        });
      } else {
        setFilters({
          ...filters,
          position: [...positions, value],
        });
      }
    } else {
      setFilters({ ...filters, [name]: value });
    }
  };

  const handleAddToFavorites = async (player) => {
    if (taken === null) {
      navigate("/login");
      return;
    }

    try {
      const userId = Number(localStorage.getItem("userId"));

      if (!favorites.some((fav) => fav.id === player.id)) {
        setFavorites([...favorites, player]);

        const response = await fetch(
          `http://localhost:8080/api/v1/favourites?userId=${userId}&playerId=${player.id}`,
          {
            method: "POST",
            headers: {
              "Content-Type": "application/json",
            },
          }
        );

        if (response.ok) {
          console.log("Favoriye ekleme başarılı!");
        } else {
          console.error("Favoriye ekleme hatası:", await response.text());
        }
      }
    } catch (error) {
      console.error("Favoriye eklerken bir hata oluştu:", error);
    }
  };

  const totalPages = Math.ceil(filteredPlayers.length / playersPerPage);

  const paginate = (pageNumber) => {
    if (pageNumber > 0 && pageNumber <= totalPages) setCurrentPage(pageNumber);
  };

  const currentPaginationGroup = Math.floor((currentPage - 1) / paginationGroupSize);
  const startPage = currentPaginationGroup * paginationGroupSize + 1;
  const endPage = Math.min(startPage + paginationGroupSize - 1, totalPages);

  const goToPreviousGroup = () => {
    const previousGroupStart = startPage - paginationGroupSize;
    if (previousGroupStart > 0) {
      setCurrentPage(previousGroupStart);
    }
  };

  const goToNextGroup = () => {
    const nextGroupStart = startPage + paginationGroupSize;
    if (nextGroupStart <= totalPages) {
      setCurrentPage(nextGroupStart);
    }
  };

  if (loading) {
    return <div className="loading">Loading players...</div>;
  }

  return (
    <div className="players-page">
      <aside className="players-sidebar">
        <h2 style={{color:"black"}}>Filters</h2>

        <div className="filter-group">
          <label htmlFor="name">Name</label>
          <input
            type="text"
            id="name"
            name="name"
            value={filters.name}
            onChange={handleFilterChange}
            placeholder="Name"
          />
        </div>

       

        <div className="filter-group">
          <label htmlFor="age">Age</label>
          <input
            type="number"
            id="age"
            name="age"
            value={filters.age}
            onChange={handleFilterChange}
            placeholder="Age"
          />
        </div>

        <div className="filter-group">
          <label htmlFor="ca">CA</label>
          <input
            type="number"
            id="ca"
            name="ca"
            value={filters.ca}
            onChange={handleFilterChange}
            placeholder="Minimum CA"
          />
        </div>

        <div className="filter-group">
          <label htmlFor="pa">PA</label>
          <input
            type="number"
            id="pa"
            name="pa"
            value={filters.pa}
            onChange={handleFilterChange}
            placeholder="Minimum PA"
          />
        </div>

        <div className="section-title"><strong>Position</strong></div>
        <div className="checkbox-group">
          {["GK", "DL", "DC", "DR", "WBL","DMC", "WBR", "ML", "MC", "MR","AML","AMC","AMR","ST"].map((pos) => (
            <label key={pos}>
              <input
                type="checkbox"
                name="position"
                value={pos}
                checked={filters.position.includes(pos)}
                onChange={handleFilterChange}
              />{" "}
              {pos}
            </label>
          ))}
        </div>
      </aside>

      <main className="players-list">
        <h1>FreeTransfers</h1>
        <table>
          <thead>
            <tr>
              <th>Add Favourites</th>
              <th>Name</th>
              <th>Club</th>
              <th>Position</th>
              <th>Salary</th>
              <th>CA</th>
              <th>PA</th>
              <th>Age</th>
            </tr>
          </thead>
          <tbody>
            {currentPlayers.map((player) => (
              <tr key={player.id}>
                <td>
                  <button
                    className="favorite-btn"
                    onClick={() => handleAddToFavorites(player)}
                  >
                    ⭐
                  </button>
                </td>
                <td>
                  <Link to={`/players/${player.id}`} className="player-link">
                    {player.name}
                  </Link>
                </td>
                <td>
                  
                    {player.club}
                  
                </td>
                <td>
                  {Array.isArray(player.position)
                    ? player.position.join(", ")
                    : player.position}
                </td>
                <td>{player.salary}€</td>
                <td>{player.ca}</td>
                <td>{player.pa}</td>
                <td>{player.age}</td>
              </tr>
            ))}
          </tbody>
        </table>

        <div className="pagination">
          <button
            className="pagination-btn"
            onClick={goToPreviousGroup}
            disabled={currentPage <= paginationGroupSize}
          >
            &lt;&lt;
          </button>

          {Array.from({ length: endPage - startPage + 1 }, (_, i) => {
            const pageNumber = startPage + i;
            return (
              <button
                key={pageNumber}
                className={`pagination-btn ${currentPage === pageNumber ? "active" : ""}`}
                onClick={() => paginate(pageNumber)}
              >
                {pageNumber}
              </button>
            );
          })}

          <button
            className="pagination-btn"
            onClick={goToNextGroup}
            disabled={startPage + paginationGroupSize > totalPages}
          >
            &gt;&gt;
          </button>
        </div>
      </main>
    </div>
  );
};

export default Players;
