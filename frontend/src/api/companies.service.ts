import { http } from './http'
import type { CompanyResponse, CreateCompanyRequest, PaginationQuery, UpdateCompanyRequest } from './types'

export const CompaniesService = {
  create: (body: CreateCompanyRequest) => http.post<void>('/api/companies', body),
  leaderboard: (query?: PaginationQuery & { sortBy?: string; desc?: boolean }) =>
    http.get<CompanyResponse[]>('/api/companies/leaderboard', query),
  me: () => http.get<CompanyResponse>('/api/companies/me'),
  update: (id: number, body: UpdateCompanyRequest) => http.put<CompanyResponse>(`/api/companies/${id}`, body),
  getById: (id: number) => http.get<CompanyResponse>(`/api/companies/${id}`),
  delete: (id: number) => http.delete<void>(`/api/companies/${id}`)
}
