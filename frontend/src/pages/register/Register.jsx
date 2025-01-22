import React, { useState } from 'react'
import { useNavigate } from 'react-router-dom'
import './register.css'

const Register = () => {

  const [name, setName] = useState("")
  const [email, setEmail] = useState("")
  const [password, setPassword] = useState("")
  const navigate = useNavigate()

  const handleNameChange = (e) => setName(e.target.value)
  const handleEmailChange = (e) => setEmail(e.target.value)
  const handlePasswordChange = (e) => setPassword(e.target.value)

  const handleSubmit = async(e) => {
    e.preventDefault()

    const userData = { name, email, password}

    try {
      const response = await fetch("http://localhost:8080/api/signup", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(userData)
      })

      const data = await response.text()
      
      if(response.ok) {
        alert("Registration succesfull")
        navigate("/login")
      } else {
        console.error("Registration failed: ", data.message)
      }
    } catch(error) {
      console.error("Error: ", error)
    }
  }

  return (
    <div className="register__header">
      <div className="register__header-container">
        <h1>Register</h1>
        <form onSubmit={handleSubmit}>
          <input
            type="text"
            placeholder='Name'
            value={name}
            onChange={handleNameChange}
          />
          <input
            type="email"
            placeholder='Email'
            value={email}
            onChange={handleEmailChange}
          />
          <input
            type="password"
            placeholder='Password'
            value={password}
            onChange={handlePasswordChange}
          />
          <button type="submit">Register</button>
        </form>
      </div>
    </div>
  )
}

export default Register