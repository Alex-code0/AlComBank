import React, { useState } from 'react';
import './navbar.css';

import logo from '../../assets/logo.png'

const Navbar = () => {

  const [activeTab, setActiveTab] = useState('fizica');

  return (
    <div className="navbar">
      <div className="navbar_buttons">
        <div className="navbar_buttons-logo">
          <a href="#home"><img src={logo} alt="logo" /></a>
        </div>
        <div className="navbar_buttons-container">
          <button onClick={() => setActiveTab("fizica")}>Persoane fizice</button>
          <button onClick={() => setActiveTab("juridica")}>Persoane juridice</button>
          <button>Despre banca</button>
        </div>
      </div>
      
      {activeTab === 'fizica' && (
        <div className="navbar_links">
          <p><a href="#credite1">Credite</a></p>
          <p><a href="#carduri1">Carduri</a></p>
          <p><a href="#depozite1">Depozite</a></p>
        </div>
      )}

      {activeTab === 'juridica' && (
        <div className="navbar_links">
          <p><a href="#credite2">Credite</a></p>
          <p><a href="#oda">ODA</a></p>
          <p><a href="#depozite2">Depozite</a></p>
          <p><a href="#carduri2">Carduri si conturi</a></p>
        </div>
      )}
    </div>
  )
}

export default Navbar