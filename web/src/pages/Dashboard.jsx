import React, { useEffect, useState } from 'react'
import { getMe } from '../api'

export default function Dashboard({ token, onLogout }) {
  const [me, setMe] = useState(null)
  const [error, setError] = useState('')

  useEffect(() => {
    if (!token) {
      setError('Not logged in')
      return
    }
    getMe(token)
      .then(setMe)
      .catch(() => setError('Unauthorized'))
  }, [token])

  return (
    <div>
      <h2>Dashboard</h2>
      {error && <p style={{ color: 'crimson' }}>{error}</p>}
      {me && (
        <div>
          <p><b>Username:</b> {me.username}</p>
          <p><b>Email:</b> {me.email}</p>
          <p><b>Role:</b> {me.role}</p>
        </div>
      )}
      <button onClick={onLogout}>Logout</button>
    </div>
  )
}
