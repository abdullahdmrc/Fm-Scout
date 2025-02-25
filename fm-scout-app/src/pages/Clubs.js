import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import "./Clubs.css"; // CSS dosyasını ekliyoruz.
import axios from "axios";
const Clubs = () => {
  const [clubs, setClubs] = useState([]);
  const [filters, setFilters] = useState({
    name: "",
    league: "",
    reputation: "",
    manager: "",
  });

  const [currentPage, setCurrentPage] = useState(1);
  const itemsPerPage = 30; // Her sayfada gösterilecek öğe sayısı
  const pagesPerGroup = 10; // Sayfa grubundaki maksimum sayfa sayısı

  const navigate = useNavigate();

  useEffect(() => {
    const fetchClubs = async () => {
      try {
        // API'ye GET isteği gönder
        const response = await axios.get("http://localhost:8080/api/v1/clubs");

        // Gelen veriyi frontend'deki yapıya uygun hale getir
        const transformedData = response.data.map((club) => ({
          id: club.clubId,
          name: club.clubName,
          league: club.leagueName,
          reputation: club.reputation,
          manager: club.managerName,
        }));
        
        // State'i güncelle
        setClubs(transformedData);
      } catch (error) {
        console.error("Error fetching clubs:", error);
      }
    };

    fetchClubs();
  }, []); // Component ilk render olduğunda çalışır
  const handleFilterChange = (e) => {
    setFilters({ ...filters, [e.target.name]: e.target.value });
    setCurrentPage(1); // Filtreleme yapıldığında ilk sayfaya dön
  };

  const filteredClubs = clubs.filter((club) =>
    Object.keys(filters).every((key) => {
      if (filters[key] === "") return true; // Eğer filtre boşsa bu alanı atla
      if (key === "reputation") {
        // Reputation sayısal bir filtre
        const filterReputation = parseInt(filters.reputation, 10);
        return club[key] >= filterReputation; // Örneğin: reputation eşik değerine eşit veya büyük
      }
      // Diğer filtreler için metinsel karşılaştırma
      return club[key]?.toLowerCase().includes(filters[key].toLowerCase());
    })
  );
  




  const indexOfLastItem = currentPage * itemsPerPage;
  const indexOfFirstItem = indexOfLastItem - itemsPerPage;
  const currentClubs = filteredClubs.slice(indexOfFirstItem, indexOfLastItem);

  const totalPages = Math.ceil(filteredClubs.length / itemsPerPage);

  const handlePageChange = (page) => {
    if (page >= 1 && page <= totalPages) {
      setCurrentPage(page);
    }
  };

  const handleGroupJump = (direction) => {
    const newPage =
      direction === "prev"
        ? Math.max(currentPage - pagesPerGroup, 1)
        : Math.min(currentPage + pagesPerGroup, totalPages);
    setCurrentPage(newPage);
  };

  const startPage = Math.floor((currentPage - 1) / pagesPerGroup) * pagesPerGroup + 1;
  const endPage = Math.min(startPage + pagesPerGroup - 1, totalPages);

  return (
    <div className="clubs-container">
      {/* Filters */}
      <aside className="filters">
        <h3>Filters</h3>
        <div className="filter-input">
          <label>Name</label>
          <input
            type="text"
            name="name"
            value={filters.name}
            onChange={handleFilterChange}
          />
        </div>
        <div className="filter-input">
          <label>League</label>
          <input
            type="text"
            name="league"
            value={filters.league}
            onChange={handleFilterChange}
          />
        </div>
        <div className="filter-input">
          <label>Reputation</label>
          <input
            type="number" 
            name="reputation"
            value={filters.reputation}
            onChange={handleFilterChange}
          />
        </div>

        <div className="filter-input">
          <label>Manager</label>
          <input
            type="text"
            name="manager"
            value={filters.manager}
            onChange={handleFilterChange}
          />
        </div>
      </aside>

      {/* Kulüp Listesi */}
      <div className="club-list">
        <h3>Clubs</h3>
        <table className="club-table">
          <thead >
            <tr>
              <th>Name</th>
              <th>League</th>
              <th>Reputation</th>
              <th>Manager</th>
            </tr>
          </thead>
          <tbody>
            {currentClubs.map((club) => (
              <tr
                key={club.id}
                className="club-row"
                onClick={() => navigate(`/clubs/${club.id}`)}
              >
                <td>{club.name}</td>
                <td>{club.league}</td>
                <td>{club.reputation}</td>
                <td>{club.manager}</td>
              </tr>
            ))}
          </tbody>
        </table>

        {/* Sayfalama */}
        <div className="pagination">
          <button
            className="pagination-button"
            onClick={() => handleGroupJump("prev")}
            disabled={currentPage === 1}
          >
            «
          </button>
          {Array.from({ length: endPage - startPage + 1 }, (_, i) => (
            <button
              key={i + startPage}
              className={`pagination-button ${currentPage === i + startPage ? "active" : ""
                }`}
              onClick={() => handlePageChange(i + startPage)}
            >
              {i + startPage}
            </button>
          ))}
          <button
            className="pagination-button"
            onClick={() => handleGroupJump("next")}
            disabled={currentPage === totalPages}
          >
            »
          </button>
        </div>
      </div>
    </div>
  );
};

export default Clubs;

