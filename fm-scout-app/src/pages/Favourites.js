import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import axios from "axios";
import "./Favourites.css";

const Favourites = () => {
  const [favorites, setFavorites] = useState([]); // Favoriler
  const [currentPage, setCurrentPage] = useState(1); // Geçerli sayfa
  const [loading, setLoading] = useState(true); // Yüklenme durumu

  const playersPerPage = 10; // Her sayfada gösterilecek oyuncu sayısı
  const paginationGroupSize = 10; // Pagination grubu büyüklüğü

  const userId = localStorage.getItem("userId"); // Kullanıcının ID'si

  // Favori oyuncuları backend'den çeken fonksiyon
  useEffect(() => {
    const fetchFavorites = async () => {
      try {
        setLoading(true);
        const response = await axios.get(
          `http://localhost:8080/api/v1/users/${userId}/favourites`
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
        setFavorites(transformedData);
        setLoading(false);
      } catch (error) {
        console.error("Favoriler çekilirken hata oluştu:", error);
        setLoading(false);
      }
    };

    fetchFavorites();
  }, [userId]);

  const indexOfLastPlayer = currentPage * playersPerPage;
  const indexOfFirstPlayer = indexOfLastPlayer - playersPerPage;
  const currentPlayers = favorites.slice(indexOfFirstPlayer, indexOfLastPlayer);

  // Favorilerden silme işlemi
  const handleRemoveFromFavorites = async (playerId) => {
    try {
      const response = await axios.delete(
        `http://localhost:8080/api/v1/favourites?userId=${userId}&playerId=${playerId}`
      );
      if (response.status === 200) {
        setFavorites(favorites.filter((player) => player.id !== playerId));
        console.log("Favoriden silme işlemi başarılı!");
      }
    } catch (error) {
      console.error("Favoriden silerken hata oluştu:", error);
    }
  };

  const totalPages = Math.ceil(favorites.length / playersPerPage);

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
    return <div className="loading">Loading favourites...</div>;
  }

  return (
    <div className="favourites-page">
      <main className="favourites-list">
        <h1>Your Favourites</h1>
        <table>
          <thead>
            <tr>
              <th>Delete Favourite</th>
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
                    className="delete-favorite-btn"
                    onClick={() => handleRemoveFromFavorites(player.id)}
                  >
                    ❌
                  </button>
                </td>
                <td>
                  <Link to={`/players/${player.id}`} className="player-link">
                    {player.name}
                  </Link>
                </td>
                <td>
                  {player.clubId ? (
                    <Link to={`/clubs/${player.clubId}`} className="club-link">
                      {player.club}
                    </Link>
                  ) : (
                    <span>No Club</span>
                  )}
                </td>
                <td>{Array.isArray(player.position) ? player.position.join(", ") : player.position}</td>
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

export default Favourites;
