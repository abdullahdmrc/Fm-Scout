
import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom"; // Link import edin
import "./Players.css";
import axios from "axios";
import { useNavigate } from "react-router-dom";


const Players = () => {
  const [players, setPlayers] = useState([]); // Tüm oyuncular
  const [currentPage, setCurrentPage] = useState(1); // Geçerli sayfa
  const [loading, setLoading] = useState(true); // Yüklenme durumu
  const [favorites, setFavorites] = useState([]); // Favoriler


  const navigate = useNavigate();




  const playersPerPage = 30; // Her sayfada 30 oyuncu gösterilecek
  const paginationGroupSize = 10; // Pagination grubu

  const taken = localStorage.getItem("userId");
  //const userId=Number(localStorage.getItem("userId"));


  /*useEffect(() => {
     const fetchPlayers = async () => {
       if (players.length === 0) { // Eğer daha önce veri çekilmediyse
         try {
           setLoading(true);
           const response = await axios.get(
             "http://localhost:8080/api/v1/football-players"
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
             clubId:player.clubId,
             
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
   }, [players.length]);*/
  useEffect(() => {
     const storedPlayers = sessionStorage.getItem("players");
     if (storedPlayers) {
       setPlayers(JSON.parse(storedPlayers));
       setLoading(false);
     } else {
       const fetchPlayers = async () => {
         setLoading(true);
         const response = await axios.get("http://localhost:8080/api/v1/football-players");
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
         sessionStorage.setItem("players", JSON.stringify(transformedData));
         setPlayers(transformedData);
         setLoading(false);
       };
 
       fetchPlayers();
     }
   }, []);
  




  const indexOfLastPlayer = currentPage * playersPerPage;
  const indexOfFirstPlayer = indexOfLastPlayer - playersPerPage;
  const currentPlayers = players.slice(indexOfFirstPlayer, indexOfLastPlayer);


  const handleAddToFavorites = async (player) => {
    if (taken === null) {
      navigate("/login"); // Giriş yapmamışsa login sayfasına yönlendir
      return;
    }

    try {
      const userId = Number(localStorage.getItem("userId")); // Giriş yapan kullanıcının ID'sini al

      // Eğer player zaten favorilerde yoksa, favoriye ekleyelim
      if (!favorites.some((fav) => fav.id === player.id)) {
        setFavorites([...favorites, player]);

        // Backend'e favori eklemek için POST isteği
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


  const totalPages = Math.ceil(players.length / playersPerPage);

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
        <h2 style={{ color: "black" }}>Filters</h2>

        <div className="filter-group">
          <label htmlFor="name">Name</label>
          <input type="text" id="name" placeholder="Name" />
        </div>


        <div className="filter-group">
          <label htmlFor="club">Club</label>
          <input type="text" id="club" placeholder="Club" />
        </div>


        <div className="filter-group">
          <label htmlFor="nationality">Nationality</label>
          <input type="text" id="nationality" placeholder="Nationality" />
        </div>

        <div className="filter-group">
          <label htmlFor="league">League</label>
          <input type="text" id="league" placeholder="League" />
        </div>





        <div className="section-title"><strong>Position</strong></div>
        <div className="checkbox-group">
          <tr>
            <label>
              <input type="checkbox" /> GK
            </label>
          </tr>
          <tr>
            <label>
              <input type="checkbox" /> DL
            </label>
          </tr>
          <tr>
            <label>
              <input type="checkbox" /> DC
            </label>
          </tr>
          <tr>
            <label>
              <input type="checkbox" /> DR
            </label>
          </tr>
          <tr>
            <label>
              <input type="checkbox" /> WBL
            </label>
          </tr>
          <tr>
            <label>
              <input type="checkbox" /> WBR
            </label>
          </tr>
          <tr>
            <label>
              <input type="checkbox" /> WBR
            </label>
          </tr>
          <tr>
            <label>
              <input type="checkbox" /> ML
            </label>
          </tr>
          <tr>
            <label>
              <input type="checkbox" /> MC
            </label>
          </tr>
          <tr>
            <label>
              <input type="checkbox" /> MR
            </label>
          </tr>
        </div>
      </aside>

      <main className="players-list">
        <h1>Players</h1>
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
              <tr key={player.id}

              >
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

export default Players;



// bu allttakı front end fıltreli
/*import React, { useState, useEffect } from "react";
import { Link } from "react-router-dom";
import "./Players.css";
import axios from "axios";
import { useNavigate } from "react-router-dom";

const Players = () => {
  const [players, setPlayers] = useState([]); // Tüm oyuncular
  const [filteredPlayers, setFilteredPlayers] = useState([]); // Filtrelenmiş oyuncular
  const [currentPage, setCurrentPage] = useState(1); // Geçerli sayfa
  const [loading, setLoading] = useState(true); // Yüklenme durumu
  const [favorites, setFavorites] = useState([]); // Favoriler

  const [filters, setFilters] = useState({
    name: "",
    club: "",
    nationality: "",
    league: "",
    position: [],
  });

  const navigate = useNavigate();

  const playersPerPage = 30; // Her sayfada 30 oyuncu gösterilecek
  const paginationGroupSize = 10; // Pagination grubu

  const taken = localStorage.getItem("userId");

  useEffect(() => {
    const storedPlayers = localStorage.getItem("players");
    if (storedPlayers) {
      const parsedPlayers = JSON.parse(storedPlayers);
      setPlayers(parsedPlayers);
      setFilteredPlayers(parsedPlayers);
      setLoading(false);
    } else {
      const fetchPlayers = async () => {
        setLoading(true);
        const response = await axios.get("http://localhost:8080/api/v1/football-players");
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
        localStorage.setItem("players", JSON.stringify(transformedData));
        setPlayers(transformedData);
        setFilteredPlayers(transformedData);
        setLoading(false);
      };
      fetchPlayers();
    }
  }, []);

  const applyFilters = () => {
    let filtered = players;

    if (filters.name) {
      filtered = filtered.filter((player) =>
        player.name.toLowerCase().includes(filters.name.toLowerCase())
      );
    }

    if (filters.club) {
      filtered = filtered.filter((player) =>
        player.club.toLowerCase().includes(filters.club.toLowerCase())
      );
    }

    if (filters.nationality) {
      filtered = filtered.filter((player) =>
        player.nationality?.toLowerCase().includes(filters.nationality.toLowerCase())
      );
    }

    if (filters.league) {
      filtered = filtered.filter((player) =>
        player.league?.toLowerCase().includes(filters.league.toLowerCase())
      );
    }

    if (filters.position.length > 0) {
      filtered = filtered.filter((player) =>
        player.position.some((pos) => filters.position.includes(pos))
      );
    }

    setFilteredPlayers(filtered);
    setCurrentPage(1); // Sayfayı başa al
  };

  const handleFilterChange = (e) => {
    const { name, value, type, checked } = e.target;

    if (type === "checkbox") {
      setFilters((prevFilters) => {
        const updatedPositions = checked
          ? [...prevFilters.position, value]
          : prevFilters.position.filter((pos) => pos !== value);
        return { ...prevFilters, position: updatedPositions };
      });
    } else {
      setFilters({ ...filters, [name]: value });
    }
  };

  const indexOfLastPlayer = currentPage * playersPerPage;
  const indexOfFirstPlayer = indexOfLastPlayer - playersPerPage;
  const currentPlayers = filteredPlayers.slice(indexOfFirstPlayer, indexOfLastPlayer);

  const handleAddToFavorites = async (player) => {
    if (taken === null) {
      navigate("/login"); // Giriş yapmamışsa login sayfasına yönlendir
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
        <h2 style={{ color: "black" }}>Filters</h2>

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
          <label htmlFor="club">Club</label>
          <input
            type="text"
            id="club"
            name="club"
            value={filters.club}
            onChange={handleFilterChange}
            placeholder="Club"
          />
        </div>

        <div className="filter-group">
          <label htmlFor="nationality">Nationality</label>
          <input
            type="text"
            id="nationality"
            name="nationality"
            value={filters.nationality}
            onChange={handleFilterChange}
            placeholder="Nationality"
          />
        </div>

        <div className="filter-group">
          <label htmlFor="league">League</label>
          <input
            type="text"
            id="league"
            name="league"
            value={filters.league}
            onChange={handleFilterChange}
            placeholder="League"
          />
        </div>

        <div className="section-title"><strong>Position</strong></div>
        <div className="checkbox-group">
          {["GK", "DL", "DC", "DR", "WBL", "WBR", "ML", "MC", "MR", "ST"].map((pos) => (
            <label key={pos}>
              <input
                type="checkbox"
                value={pos}
                checked={filters.position.includes(pos)}
                onChange={handleFilterChange}
              />
              {pos}
            </label>
          ))}
        </div>

        <button className="apply-filters-btn" onClick={applyFilters}>
          Apply Filters
        </button>
      </aside>

      <main className="players-list">
        <h1>Players</h1>
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

export default Players;*/



