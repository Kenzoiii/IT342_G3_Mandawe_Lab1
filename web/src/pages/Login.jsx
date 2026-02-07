import React, { useState } from 'react'
import { login } from '../api'

export default function Login({ onLogin }) {
  const [email, setEmail] = useState('')
  const [password, setPassword] = useState('')
  const [message, setMessage] = useState('')

  const submit = async (e) => {
    e.preventDefault()
    setMessage('')
    try {
      const res = await login({ email, password })
      if (res.token) {
        onLogin(res.token)
      } else {
        setMessage(res.message || 'Login failed')
      }
    } catch (err) {
      setMessage('Network error')
    }
  }

  return (
    <form onSubmit={submit}>
      <h2>Login</h2>
      <div>
        <label>Email</label>
        <input value={email} onChange={e => setEmail(e.target.value)} type="email" required />
      </div>
      <div>
        <label>Password</label>
        <input value={password} onChange={e => setPassword(e.target.value)} type="password" required />
      </div>
      <button type="submit">Login</button>
      {message && <p style={{ color: 'crimson' }}>{message}</p>}
    </form>
  )
}
