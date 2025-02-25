import React from 'react';
import './HomePage.css';
import { Link } from "react-router-dom";
import backgroundVideo from './background2.mp4'; // Video dosyasını import edin

const Homepage = () => {
  return (
    <div className="homepage-container">
      {/* Arka plan videosu */}
      <video className="background-video" autoPlay loop muted>
        <source src={backgroundVideo} type="video/mp4" />
        Your browser does not support the video tag.
      </video>

      {/* İçerik */}
      <p>Football Manager Scout</p>
      <div className="buttons-and-logo">

       
        <tr>
        <td>
        <div className="left-buttons">
          <tr>
            <td><Link to="/Players" className="button">Players</Link></td>
            <td><Link to="/Clubs" className="button">Clubs</Link></td>
            <td><Link to="/favourites" className="button">Favourites</Link></td>
          </tr>

        </div>
        </td>

        
        <td>
        <div className="right-buttons">
          <tr>
            <td><Link to="/leagues" className="button">Leagues</Link></td>
            <td> 
            <Link to="/wonderkids" className="button">Wonderkids</Link></td>
            <td><Link to="/freetransfers" className="button">FreeTransfers</Link></td>
            
            <td><Link to="/login" className="button">Login</Link></td>
          </tr>
         
          
          
        </div>
        </td>
        
        </tr>
        
      </div>
    </div>
  );
};

export default Homepage;
