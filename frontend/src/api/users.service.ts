import { http, setToken, getToken } from './http'
import type { LoginRequest, RegisterRequest, TokenResponse, UserRequest, UserResponse } from './types'

export const UsersService = {
  login: async (body: LoginRequest) => {
    const res = await http.post<TokenResponse>('/api/users/login', body)
    if (res?.token) setToken(res.token)
    return res
  },
  register: (body: RegisterRequest) => http.post<void>('/api/users/register', body),
  me: {
    get: () => http.get<UserResponse>('/api/users/me'),
    update: (body: UserRequest) => http.put<UserResponse>('/api/users/me', body),
    delete: () => http.delete<void>('/api/users/me')
  },
  admin: {
    deleteUser: (id: number) => http.delete<void>(`/api/users/${id}`)
  },
  getToken
}
