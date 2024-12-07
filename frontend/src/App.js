import React, { useEffect, useState } from 'react'
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom'

import { Home, LogIn, Register, Account } from './pages'
import { ProtectedRoutes } from './routes'
import './App.css'

const App = () => {
  const [user, setUser] = useState(null)

  useEffect(() => {
    const storedUser = JSON.parse(localStorage.getItem('userData'))
    if (storedUser) {
      setUser(storedUser)
    }
  }, [])

  const handleLogin = (userData) => {
    setUser(userData)
    localStorage.setItem('userData', JSON.stringify(userData))
  }

  return (
    <Router>
      <Routes>
        <Route path="/" element={<Home user={user} />} />
        <Route path="/login" element={<LogIn onLogin={handleLogin} />} />
        <Route path="/register" element={<Register />} />
        <Route
          path="/account"
          element={
            <ProtectedRoutes user={user}>
              <Account />
            </ProtectedRoutes>
          }
        />
      </Routes>
    </Router>
  );
};

export default App