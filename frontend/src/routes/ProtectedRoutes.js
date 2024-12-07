import React from 'react'
import { Navigate } from 'react-router-dom'

const ProtectedRoutes = ({ children }) => {
  if (localStorage.getItem("accountData")) {
    return children
  } else {
    <Navigate to="/login" />
  }
};

export default ProtectedRoutes