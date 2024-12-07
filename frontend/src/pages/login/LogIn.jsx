import React, { useState } from 'react'
import { useNavigate, Link } from 'react-router-dom'
import './logIn.css'

const LogIn = ({ onLogin }) => {
  const [email, setEmail] = useState("")
  const [password, setPassword] = useState("")
  const navigate = useNavigate()

  const handleEmailChange = (e) => setEmail(e.target.value)
  const handlePasswordChange = (e) => setPassword(e.target.value)

  const handleSubmit = async (e) => {
    e.preventDefault()
    const userData = { email, password }

    try {
      const response = await fetch("http://localhost:8080/api/login", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(userData),
      });
      
      const data = await response.json()

      if (response.ok) {
        localStorage.setItem("authToken", data.token)
        localStorage.setItem("accountData", JSON.stringify(data.account))
        onLogin(data.user)
        navigate("/")
      } else {
        console.error("Login failed:", data.message)
      }
    } catch (error) {
      console.error("Error:", error)
    }
  };

  return (
    <div className="login">
      <span>Log in</span>
      <form onSubmit={handleSubmit}>
        <div className="input">
          <img src="" alt="" />
          <input type="text" placeholder="Email" value={email} onChange={handleEmailChange} />
        </div>
        <div className="input">
          <img src="" alt="" />
          <input type="password" placeholder="Password" value={password} onChange={handlePasswordChange} />
        </div>
        <button type="submit">Log in</button>
        <Link to="/register">Register</Link>
      </form>
    </div>
  );
};

export default LogIn