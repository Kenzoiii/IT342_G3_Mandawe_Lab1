import React, { useState } from 'react'
import { register } from '../api'

export default function Register() {
  const [username, setUsername] = useState('')
  const [email, setEmail] = useState('')
  const [password, setPassword] = useState('')
  const [message, setMessage] = useState('')

  const submit = async (e) => {
    e.preventDefault()
    setMessage('')
    try {
      const res = await register({ username, email, password })
      setMessage(res.message || 'Registration done')
    } catch (err) {
      setMessage('Network error')
    }
  }

  return (
    <form onSubmit={submit}>
      <h2>Register</h2>
      <div>
        <label>Username</label>
        <input value={username} onChange={e => setUsername(e.target.value)} required />
      </div>
      <div>
        <label>Email</label>
        <input value={email} onChange={e => setEmail(e.target.value)} type="email" required />
      </div>
      <div>
        <label>Password</label>
        <input value={password} onChange={e => setPassword(e.target.value)} type="password" required />
      </div>
      <button type="submit">Register</button>
      {message && <p style={{ color: 'teal' }}>{message}</p>}
    </form>
  )
}
