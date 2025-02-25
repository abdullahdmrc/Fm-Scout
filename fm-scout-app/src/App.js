/*import React, { useState } from "react";
import { BrowserRouter as Router, Route, Routes, useLocation } from "react-router-dom";
import HomePage from "./components/HomePage";
import NavBar from "./components/NavBar";
import Players from "./pages/Players";
import Clubs from "./pages/Clubs";
import Wonderkids from "./pages/wonderkids";
import FreeTransfers from "./pages/freetransfers";
import Favourites from "./pages/Favourites";
import Leagues from "./pages/leagues";
import Login from "./pages/login";
import ClubDetails from "./pages/ClubDetails"; 

import SignUp from "./pages/SignUp"
import PlayerDetails from "./pages/PlayerDetails";
import LeagueDetails from "./pages/LeagueDetails";



function App() {
  const location = useLocation(); // Mevcut rotayı kontrol eder
  const [favorites, setFavorites] = useState([]); // Favoriler için state


  
  return (
    <>
     
      {location.pathname !== "/" && <NavBar />}
      <Routes>
        <Route path="/" element={<HomePage />} />
        <Route path="/players" element={<Players favorites={favorites} setFavorites={setFavorites} />} />
        <Route path="/players/:player_id" element={<PlayerDetails />} />
        <Route path="/clubs" element={<Clubs />} />
        <Route path="/clubs/:clubId" element={<ClubDetails />} />
        <Route path="/wonderkids" element={<Wonderkids />} />
        <Route path="/freetransfers" element={<FreeTransfers />} />
        <Route path="/favourites" element={<Favourites favorites={favorites} />} />
        <Route path="/leagues" element={<Leagues />} />
        <Route path="/leagues/:leagueId" element={<LeagueDetails />} />

        <Route path="/login" element={<Login />} />
        <Route path="/signup" element={<SignUp />} />
        
      </Routes>
    </>
  );
}



export default App;*/
import React, { useState, useEffect } from "react";
import { BrowserRouter as Router, Route, Routes, useLocation } from "react-router-dom";
import HomePage from "./components/HomePage";
import NavBar from "./components/NavBar";
import Players from "./pages/Players";
import Clubs from "./pages/Clubs";
import Wonderkids from "./pages/wonderkids";
import FreeTransfers from "./pages/freetransfers";
import Favourites from "./pages/Favourites";
import Leagues from "./pages/leagues";
import Login from "./pages/login";
import ClubDetails from "./pages/ClubDetails";
import SignUp from "./pages/SignUp";
import PlayerDetails from "./pages/PlayerDetails";
import LeagueDetails from "./pages/LeagueDetails";
import Operations from "./pages/Operations";
function App() {
  const location = useLocation(); // Mevcut rotayı kontrol eder
  const [favorites, setFavorites] = useState([]); // Favoriler için state
  const [isAdmin, setIsAdmin] = useState(false); // Admin durumunu saklamak için state
  const [isLoggedIn, setIsLoggedIn] = useState(false); // Kullanıcı giriş durumu

  // Sayfa her yenilendiğinde, localStorage'dan durumu al
  useEffect(() => {
    const loggedIn = localStorage.getItem("isLoggedIn") === "true";
    const adminStatus = localStorage.getItem("isAdmin") === "true";

    setIsLoggedIn(loggedIn);
    setIsAdmin(adminStatus);
  }, []);

  return (
    <>
      {/* NavBar sadece HomePage dışında göster */}
      {location.pathname !== "/" && <NavBar isAdmin={isAdmin} isLoggedIn={isLoggedIn} setIsLoggedIn={setIsLoggedIn} />}
      <Routes>
        <Route path="/" element={<HomePage />} />
        <Route path="/players" element={<Players favorites={favorites} setFavorites={setFavorites} isLoggedIn={isLoggedIn} />} />
        <Route path="/players/:player_id" element={<PlayerDetails />} />
        <Route path="/clubs" element={<Clubs />} />
        <Route path="/clubs/:clubId" element={<ClubDetails />} />
        <Route path="/wonderkids" element={<Wonderkids />} />
        <Route path="/freetransfers" element={<FreeTransfers />} />
        <Route path="/favourites" element={<Favourites favorites={favorites} />} />
        <Route path="/leagues" element={<Leagues />} />
        <Route path="/Operations" element={<Operations />} />
        <Route path="/leagues/:leagueId" element={<LeagueDetails />} />
        <Route path="/login" element={<Login setIsLoggedIn={setIsLoggedIn} setIsAdmin={setIsAdmin} />} />
        <Route path="/signup" element={<SignUp />} />
      </Routes>
    </>
  );
}

export default App;

