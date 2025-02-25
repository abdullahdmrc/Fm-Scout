import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom"; // useNavigate'i ekliyoruz
import axios from "axios";
import "./Leagues.css"; // CSS dosyasını unutmayın

const Leagues = () => {
  const [leagues, setLeagues] = useState([]); // Lig verileri
  const [loading, setLoading] = useState(true); // Yüklenme durumu
  const [search, setSearch] = useState(""); // Arama filtresi
  const [tierFilter, setTierFilter] = useState(""); // Lig seviyesi filtresi
  const [countryFilter, setCountryFilter] = useState(""); // Ülke filtresi
  const [currentPage, setCurrentPage] = useState(1); // Mevcut sayfa
  const leaguesPerPage = 10; // Her sayfada gösterilecek lig sayısı

  const navigate = useNavigate(); // React Router navigate fonksiyonu

  useEffect(() => {
    const fetchLeagues = async () => {
      try {
        setLoading(true);
        const response = await axios.get("http://localhost:8080/api/v1/leagues");
        setLeagues(response.data); // Backend'den gelen veriyi kaydediyoruz
        setLoading(false);
      } catch (error) {
        console.error("Error fetching leagues:", error);
        setLoading(false);
      }
    };

    fetchLeagues();
  }, []);

  // Filtrelenmiş ligleri hesapla
  const filteredLeagues = leagues.filter((league) => {
    const matchesSearch = league.leagueName.toLowerCase().includes(search.toLowerCase());
    const matchesTier = tierFilter ? league.leagueTier === parseInt(tierFilter) : true;
    const matchesCountry = countryFilter
      ? league.countryName.toLowerCase().includes(countryFilter.toLowerCase())
      : true;
    return matchesSearch && matchesTier && matchesCountry;
  });

  // Sayfa başına gösterilecek ligleri hesapla
  const startIndex = (currentPage - 1) * leaguesPerPage;
  const currentLeagues = filteredLeagues.slice(startIndex, startIndex + leaguesPerPage);

  // Toplam sayfa sayısı
  const totalPages = Math.ceil(filteredLeagues.length / leaguesPerPage);

  // Sayfa değiştir
  const paginate = (pageNumber) => {
    if (pageNumber > 0 && pageNumber <= totalPages) {
      setCurrentPage(pageNumber);
    }
  };

  if (loading) {
    return <div className="loading">Loading leagues...</div>;
  }

  return (
    <div className="leagues-page">
      <h1>Leagues</h1>

      {/* Filtreleme Alanı */}
      <div className="filters">
        <input
          type="text"
          placeholder="League Name"
          value={search}
          onChange={(e) => setSearch(e.target.value)}
          className="filter-input"
        />
        <input
          type="text"
          placeholder="Tier"
          value={tierFilter}
          onChange={(e) => setTierFilter(e.target.value)}
          className="filter-input"
        />
        <input
          type="text"
          placeholder="Country name"
          value={countryFilter}
          onChange={(e) => setCountryFilter(e.target.value)}
          className="filter-input"
        />
      </div>

      {/* Lig Tablosu */}
      <table className="leagues-table">
        <thead>
          <tr>
            <th>League Name</th>
            <th>Tier</th>
            <th>Country</th>
          </tr>
        </thead>
        <tbody>
          {currentLeagues.map((league) => (
            <tr
              key={league.leagueId} // Backend'den gelen ID'yi burada kullanın
              onClick={() => navigate(`/leagues/${league.leagueId}`)} // Tıklanabilirlik için
              className="clickable-row" // CSS ile işaretlemek için bir sınıf
            >
              <td>{league.leagueName}</td>
              <td>{league.leagueTier}</td>
              <td>{league.countryName}</td>
            </tr>
          ))}
        </tbody>
      </table>

      {currentLeagues.length === 0 && <p>No leagues found.</p>}

      {/* Sayfalama (Pagination) */}
      <div className="pagination">
        <button
          className="pagination-btn"
          onClick={() => paginate(currentPage - 1)}
          disabled={currentPage === 1}
        >
          &lt; Previous
        </button>
        {Array.from({ length: totalPages }, (_, index) => (
          <button
            key={index + 1}
            className={`pagination-btn ${currentPage === index + 1 ? "active" : ""}`}
            onClick={() => paginate(index + 1)}
          >
            {index + 1}
          </button>
        ))}
        <button
          className="pagination-btn"
          onClick={() => paginate(currentPage + 1)}
          disabled={currentPage === totalPages}
        >
          Next &gt;
        </button>
      </div>
    </div>
  );
};

export default Leagues;
