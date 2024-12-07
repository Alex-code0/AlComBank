import React from 'react'
import { NavBar } from '../../components'
import './home.css'

const Home = ({ user }) => {
  return (
    <div className="gradient__bg">
        <NavBar user={user}/>
    </div>
  )
}

export default Home