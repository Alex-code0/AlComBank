import React, { useState, useEffect } from 'react'
import { useNavigate, Link } from 'react-router-dom'
import './navBar.css'

const NavBar = ({ user }) => {
  const [dropdownOpen, setDropdownOpen] = useState(false)
  const [localUser, setLocalUser] = useState(user)

  const toggleDropdown = () => setDropdownOpen(!dropdownOpen)

  const navigate = useNavigate()

  const handleLogout = () => {
    localStorage.removeItem("userData")
    localStorage.removeItem("accountData")
    localStorage.removeItem("authToken")
    setLocalUser(null)
    navigate("/")
    window.location.reload()
  }

  window.handleLogout = handleLogout

  useEffect(() => {
    if (!localUser) {
      const storedUser = JSON.parse(localStorage.getItem('userData'))
      if (storedUser) {
        setLocalUser(storedUser)
      }
    }
  }, [localUser])

  return (
    <div className="alcombank__navbar">
      <div className="alcombank__navbar-links">
        <div className="alcombank__navbar-links_logo">
          <p>AlComBank</p>
        </div>
        <div className="alcombank__navbar-links_container">
          <p><a href="#home">Persoana fizică</a></p>
          <p><a href="#home">Persoana juridică</a></p>
          <p><a href="#home">Despre bancă</a></p>
        </div>
      </div>
      {localUser ? (
        <div className="alcombank__navbar-logedin">
          <p onClick={toggleDropdown}>Welcome, {localUser}</p>
          {dropdownOpen && (
            <ul>
              <li>
                <Link to="/account" onClick={toggleDropdown}>Account</Link>
              </li>
              <li>Settings</li>
              <li onClick={handleLogout}>Logout</li>
            </ul>
          )}
        </div>
      ) : (
        <div className="alcombank__navbar-auth">
          <Link to="/login">
            <button type="button">Sign in</button>
          </Link>
        </div>
      )}
    </div>
  )
}

export default NavBar