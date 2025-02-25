/*import React from "react";
import { Link } from "react-router-dom";
import { FaUserFriends, FaStar, FaHome, FaFootballBall, FaHandsHelping, FaListUl } from "react-icons/fa"; // İkonları içe aktar
import "./NavBar.css";

const NavBar = () => {
  return (
    <nav className="navbar">
      <div className="navbar-container">
        <ul className="navbar-links">
          <li>
            <Link to="/Players">
              <FaUserFriends style={{ marginRight: "8px" }} />
              Players
            </Link>
          </li>
          <li>
            <Link to="/wonderkids">
              <FaStar style={{ marginRight: "8px" }} />
              Wonderkids
            </Link>
          </li>
          <li>
            <Link to="/freetransfers">
              <FaHandsHelping style={{ marginRight: "8px" }} />
              FreeTransfers
            </Link>
          </li>
          <li>
            <Link to="/Clubs">
              <FaFootballBall style={{ marginRight: "8px" }} />
              Clubs
            </Link>
          </li>
          <li>
            <Link to="/favourites">
              <FaStar style={{ marginRight: "8px" }} />
              Favourites
            </Link>
          </li>
          <li>
            <Link to="/leagues">
              <FaListUl style={{ marginRight: "8px" }} />
              Leagues
            </Link>
          </li>

          <li>
            <Link to="/Operations">
              <FaListUl style={{ marginRight: "8px" }} />
              Operations
            </Link>
          </li>
         
        </ul>
        <div className="login-button">
          <Link to="/login">
            <FaHome style={{ marginRight: "8px" }} />
            Log In
          </Link>
        </div>
      </div>
    </nav>
  );
};

export default NavBar;*/

import React from "react";
import { Link, useNavigate } from "react-router-dom"; // useNavigate'ı içe aktar
import { FaUserFriends, FaStar, FaHome, FaFootballBall, FaHandsHelping, FaListUl } from "react-icons/fa"; // İkonları içe aktar


import { FaCog } from "react-icons/fa";

import "./NavBar.css";

const NavBar = () => {
  const navigate = useNavigate(); // useNavigate hook'u
  const userId = localStorage.getItem("userId"); // userId'yi kontrol et
  const isAdmin = JSON.parse(localStorage.getItem("isAdmin")) === true; // Boolean olarak dönüştür
  //const isAdmin = JSON.parse(localStorage.getItem("isAdmin"));
  const handleLogout = async () => {
    try {
      // Logout işlemi
      const response = await fetch("http://localhost:8080/api/v1/users/logout", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
      });

      if (response.ok) {
        // Kullanıcı bilgilerini temizle
        localStorage.removeItem("userId");
        localStorage.removeItem("isAdmin");
        navigate("");
      }
    } catch (error) {
      console.error("Logout Error:", error);
    }
  };
  
  return (
    <nav className="navbar">
      <div className="navbar-container">
        <ul className="navbar-links">
          <li>
            <Link to="/Players">
              <FaUserFriends style={{ marginRight: "8px" }} />
              Players
            </Link>
          </li>
          <li>
            <Link to="/wonderkids">
              <FaStar style={{ marginRight: "8px" }} />
              Wonderkids
            </Link>
          </li>
          <li>
            <Link to="/freetransfers">
              <FaHandsHelping style={{ marginRight: "8px" }} />
              FreeTransfers
            </Link>
          </li>
          <li>
            <Link to="/Clubs">
              <FaFootballBall style={{ marginRight: "8px" }} />
              Clubs
            </Link>
          </li>
          <li>
            <Link to="/favourites">
              <FaStar style={{ marginRight: "8px" }} />
              Favourites
            </Link>
          </li>
          <li>
            <Link to="/leagues">
              <FaListUl style={{ marginRight: "8px" }} />
              Leagues
            </Link>
          </li>
          {isAdmin && (
            <li>
              <Link to="/Operations">
                <FaCog style={{ marginRight: "8px" }} />
                Operations
              </Link>
            </li>
          )}
        </ul>
        <div className="login-button">
          {userId ? (
            <button onClick={handleLogout}>
              <FaHome style={{ marginRight: "8px" }} />
              Log Out
            </button>
          ) : (
            <Link to="/login">
              <FaHome style={{ marginRight: "8px" }} />
              Log In
            </Link>
          )}
        </div>
      </div>
    </nav>
  );
};

export default NavBar;
