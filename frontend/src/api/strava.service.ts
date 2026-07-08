import { http } from './http'
import type { 
  StravaAuthUrlResponse, 
  StravaCommuteCandidateResponse, 
  StravaImportRequest, 
  StravaImportResponse 
} from './types'

export const StravaService = {
  getAuthUrl: () => http.get<StravaAuthUrlResponse>('/api/strava/auth-url'),
  getCommuteCandidates: () => http.get<StravaCommuteCandidateResponse[]>('/api/strava/commute-candidates'),
  importCommuteCandidates: (body: StravaImportRequest) => 
    http.post<StravaImportResponse>('/api/strava/commute-candidates/import', body),
}
