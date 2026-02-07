import React, { useEffect, useState } from 'react'
import Login from './pages/Login'
import Register from './pages/Register'
import Dashboard from './pages/Dashboard'

function App() {
  const [route, setRoute] = useState('login')
  const [token, setToken] = useState(localStorage.getItem('token') || '')

  useEffect(() => {
    const hash = window.location.hash.replace('#', '')
    if (hash) setRoute(hash)
    window.addEventListener('hashchange', () => {
      setRoute(window.location.hash.replace('#', '') || 'login')
    })
  }, [])

  const onLogin = (t) => {
    setToken(t)
    localStorage.setItem('token', t)
    window.location.hash = 'dashboard'
  }

  const onLogout = () => {
    localStorage.removeItem('token')
    setToken('')
    window.location.hash = 'login'
  }

  return (
    <div style={{ maxWidth: 480, margin: '40px auto', fontFamily: 'system-ui' }}>
      <h1>IT342 Auth Web</h1>
      <nav style={{ marginBottom: 20 }}>
        <a href="#login" style={{ marginRight: 10 }}>Login</a>
        <a href="#register" style={{ marginRight: 10 }}>Register</a>
        <a href="#dashboard">Dashboard</a>
      </nav>
      {route === 'register' && <Register />}
      {route === 'login' && <Login onLogin={onLogin} />}
      {route === 'dashboard' && <Dashboard token={token} onLogout={onLogout} />}
    </div>
  )
}

export default App
