export type LocalDateTime = string

export interface CompanyResponse {
  id?: number
  name?: string
  sirenNumber?: string
  totalEmployees?: number
  totalCo2Saved?: number
  totalPoints?: number
  totalKm?: number
  logoPath?: string
}

export interface CreateCompanyRequest {
  companyName: string
  sirenNumber: string
  logoPath?: string
}

export interface UpdateCompanyRequest {
  companyName: string
  logoPath?: string
}

export interface LoginRequest {
  email: string
  password: string
}

export interface RegisterRequest {
  name: string
  email: string
  password: string
  companyId?: number
}

export interface TokenResponse {
  token?: string
  tokenType?: string
  expiresIn?: number
}

export type TransportType = 'VELO' | 'TROTINETTE' | 'MARCHE' | 'COVOITURAGE' | 'TRANSPORTS_COMMUNS'

export interface TripRequest {
  distanceKm: number
  type: TransportType
}

export interface TripResponse {
  id?: number
  distanceKm?: number
  co2Saved?: number
  pointsEarned?: number
  type?: TransportType
  createdAt?: LocalDateTime
}

export interface UserRequest {
  name: string
  email: string
}

export interface UserResponse {
  id?: number
  name?: string
  email?: string
  role?: string
  carbonPointsBalance?: number
  totalCo2Saved?: number
}

export interface PaginationQuery {
  desc?: boolean
  page?: number
  size?: number
  sortBy?: string
}
