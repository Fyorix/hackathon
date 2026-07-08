type Query = Record<string, unknown>

function buildQuery(query?: Query) {
  if (!query) return ''
  const params = new URLSearchParams()
  Object.entries(query).forEach(([k, v]) => {
    if (v === undefined || v === null) return
    params.append(k, String(v))
  })
  const s = params.toString()
  return s ? `?${s}` : ''
}

let baseUrl = import.meta.env?.VITE_API_BASE_URL || ''
let token: string | null = (typeof localStorage !== 'undefined' && localStorage.getItem('auth_token')) || null

export function setBaseUrl(url: string) {
  baseUrl = url || ''
}

export function setToken(t: string | null | undefined) {
  token = t ?? null
  if (typeof localStorage !== 'undefined') {
    if (token) localStorage.setItem('auth_token', token)
    else localStorage.removeItem('auth_token')
  }
}

export function clearToken() {
  setToken(null)
}

export function getToken() {
  return token
}

export function isAuthenticated() {
  return !!token
}

async function request<T>(method: string, path: string, options?: { query?: Query; body?: unknown; headers?: Record<string, string> }) {
  const url = `${baseUrl}${path}${buildQuery(options?.query)}`
  const headers: Record<string, string> = { 'Accept': 'application/json', ...(options?.headers || {}) }
  if (options?.body !== undefined) headers['Content-Type'] = 'application/json'
  if (token) {
    headers['Authorization'] = `Bearer ${token}`
    if (token.startsWith('mock-jwt-token-for-')) {
      headers['X-User-Email'] = token.substring('mock-jwt-token-for-'.length())
    }
  }
  const res = await fetch(url, { method, headers, body: options?.body !== undefined ? JSON.stringify(options.body) : undefined })
  if (res.status === 204) return undefined as unknown as T
  const text = await res.text()
  if (!res.ok) {
    const err: any = new Error(text || res.statusText)
    err.status = res.status
    err.body = text
    throw err
  }
  if (!text) return undefined as unknown as T
  try {
    return JSON.parse(text) as T
  } catch {
    return undefined as unknown as T
  }
}

export const http = {
  get: <T>(path: string, query?: Query, headers?: Record<string, string>) => request<T>('GET', path, { query, headers }),
  post: <T>(path: string, body?: unknown, headers?: Record<string, string>) => request<T>('POST', path, { body, headers }),
  put: <T>(path: string, body?: unknown, headers?: Record<string, string>) => request<T>('PUT', path, { body, headers }),
  delete: <T>(path: string, query?: Query, headers?: Record<string, string>) => request<T>('DELETE', path, { query, headers })
}
