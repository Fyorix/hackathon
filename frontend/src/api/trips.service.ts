import { http } from './http'
import type { PaginationQuery, TripRequest, TripResponse } from './types'

export const TripsService = {
  list: (query?: PaginationQuery & { sortBy?: string; desc?: boolean }) =>
    http.get<TripResponse[]>('/api/trips', query),
  create: (body: TripRequest) => http.post<void>('/api/trips', body),
  delete: (id: number) => http.delete<void>(`/api/trips/${id}`)
}
