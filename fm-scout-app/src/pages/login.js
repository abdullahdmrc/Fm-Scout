

import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import "./Login.css";
import { FaEnvelope, FaLock } from "react-icons/fa";

const Login = () => {
  const [userName, setUserName] = useState("");
  const [password, setPassword] = useState("");
  const [errorMessage, setErrorMessage] = useState("");
  //const[isLoggedIn,setIsLoggedIn]=useState(false);
  const navigate = useNavigate();

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      // Backend'e GET isteği gönder
      const response = await fetch(
        `http://localhost:8080/api/v1/users/login?userName=${userName}&password=${password}`,
        {
          method: "GET",
          headers: {
            "Content-Type": "application/json",
        },
        }
      );
      const userData = await response.json(); 
      if (response.ok) {
        localStorage.setItem("userId",userData.userId.userId);
        localStorage.setItem("isAdmin", JSON.stringify(userData.userId.isAdmin));
        
        if (userData.userId.isAdmin === true) {
          navigate("/clubs"); // Admin paneline yönlendir
        } else {
          navigate("/players"); // Kullanıcı paneline yönlendir
        }
      } else if (response.status === 400 || response.status === 401) {
        setErrorMessage("Invalid username or password.");
      } else {
        setErrorMessage("An error occurred. Please try again later.");
      }
    } catch (error) {
      console.error("Error:", error);
      setErrorMessage("An error occurred. Please try again.");
    }
  };

  return (
    <div className="login-container">
      <div className="login-box">
        <h2>Welcome Back</h2>
        <form onSubmit={handleSubmit}>
          <div className="textbox">
            <FaEnvelope className="input-icon" />
            <input
              type="text"
              placeholder="User Name"
              value={userName}
              onChange={(e) => setUserName(e.target.value)}
              required
            />
          </div>
          <div className="textbox">
            <FaLock className="input-icon" />
            <input
              type="password"
              placeholder="Password"
              value={password}
              onChange={(e) => setPassword(e.target.value)}
              required
            />
          </div>
          {errorMessage && <p className="error-message">{errorMessage}</p>}
          <div className="btn-container">
            <button type="submit" className="login-btn">
              Login
            </button>
          </div>
        </form>
        <p className="signup-link">
          Don't have an account? <a href="/signup">Sign up</a>
        </p>
        
      </div>
    </div>
  );
};

export default Login;
/*import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import "./Login.css";
import { FaEnvelope, FaLock } from "react-icons/fa";

const Login = ({ setIsLoggedIn, setIsAdmin }) => {
  const [userName, setUserName] = useState("");
  const [password, setPassword] = useState("");
  const [errorMessage, setErrorMessage] = useState("");
  const [isLoggedIn, setIsLoggedInState] = useState(false); // Kullanıcı durumu
  const navigate = useNavigate();

  // Giriş yapma işlemi
  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
      const response = await fetch(
        `http://localhost:8080/api/v1/users/login?userName=${userName}&password=${password}`,
        {
          method: "GET",
          headers: {
            "Content-Type": "application/json",
          },
        }
      );
      const userData = await response.json();
      if (response.ok) {
        setIsLoggedIn(true);
        setIsAdmin(userData.isAdmin); // Admin kontrolü
        setIsLoggedInState(true); // Kullanıcıyı giriş yapmış olarak işaretle
        
        localStorage.setItem("userId", userData.userId); // userId'yi localStorage'a kaydet


        if (userData.isAdmin) {
          setIsLoggedIn(true);
          localStorage.setItem("isLoggedIn", "true");  // Giriş durumu saklanıyor
          localStorage.setItem("userId", userData.userId); // userId'yi localStorage'a kaydet

          navigate("/players"); // Admin paneline yönlendir
        } else {
          setIsLoggedIn(true);
          localStorage.setItem("isLoggedIn", "true");  // Giriş durumu saklanıyor
          localStorage.setItem("userId", userData.userId); // userId'yi localStorage'a kaydet

          navigate("/players"); // Kullanıcı paneline yönlendir
        }
      } else if (response.status === 400 || response.status === 401) {
        setErrorMessage("Invalid username or password.");
      } else {
        setErrorMessage("An error occurred. Please try again later.");
      }
    } catch (error) {
      console.error("Error:", error);
      setErrorMessage("An error occurred. Please try again.");
    }
  };

  // Çıkış yapma işlemi
  const handleLogout = async () => {
    try {
      // Backend'e logout isteği gönder
      const response = await fetch("http://localhost:8080/api/v1/users/logout", {
        method: "POST",
        credentials: "include", // Eğer oturum çerezi kullanıyorsanız
      });

      if (response.ok) {
        setIsLoggedIn(false); // Kullanıcıyı çıkış yapmış olarak işaretle
        setIsAdmin(false); // Admin kontrolü sıfırlanır
        setIsLoggedInState(false); // Kullanıcı durumu sıfırlanır
        localStorage.removeItem("authToken"); // Eğer varsa token'ı sil
        navigate("/"); // Çıkış sonrası ana sayfaya yönlendir
      } else {
        console.error("Logout failed");
      }
    } catch (error) {
      console.error("Logout error:", error);
    }
  };

  return (
    <div className="login-container">
      <div className="login-box">
        <h2>Welcome Back</h2>
        {!isLoggedIn ? (
          <form onSubmit={handleSubmit}>
            <div className="textbox">
              <FaEnvelope className="input-icon" />
              <input
                type="text"
                placeholder="User Name"
                value={userName}
                onChange={(e) => setUserName(e.target.value)}
                required
              />
            </div>
            <div className="textbox">
              <FaLock className="input-icon" />
              <input
                type="password"
                placeholder="Password"
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                required
              />
            </div>
            {errorMessage && <p className="error-message">{errorMessage}</p>}
            <div className="btn-container">
              <button type="submit" className="login-btn">
                Login
              </button>
            </div>
          </form>
        ) : (
          <div>
            <h3>You are logged in</h3>
            <button onClick={handleLogout} className="logout-btn">
              Log Out
            </button>
          </div>
        )}
        {!isLoggedIn && (
          <p className="signup-link">
            Don't have an account? <a href="/signup">Sign up</a>
          </p>
        )}
      </div>
    </div>
  );
};

export default Login;*/
