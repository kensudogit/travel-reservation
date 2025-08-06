import axios, { AxiosInstance, AxiosResponse } from 'axios';
import { 
  User, 
  Destination, 
  Tour, 
  Reservation, 
  LoginRequest, 
  LoginResponse, 
  CreateReservationRequest,
  SearchFilters,
  ApiResponse 
} from '@/types';

class ApiService {
  private api: AxiosInstance;

  constructor() {
    this.api = axios.create({
      baseURL: process.env.NEXT_PUBLIC_API_URL || 'http://localhost:8080/api',
      headers: {
        'Content-Type': 'application/json',
      },
    });

    // Request interceptor to add auth token
    this.api.interceptors.request.use((config) => {
      const token = localStorage.getItem('token');
      if (token) {
        config.headers.Authorization = `Bearer ${token}`;
      }
      return config;
    });

    // Response interceptor to handle errors
    this.api.interceptors.response.use(
      (response) => response,
      (error) => {
        if (error.response?.status === 401) {
          localStorage.removeItem('token');
          localStorage.removeItem('user');
          window.location.href = '/login';
        }
        return Promise.reject(error);
      }
    );
  }

  // Auth endpoints
  async login(credentials: LoginRequest): Promise<LoginResponse> {
    const response: AxiosResponse<LoginResponse> = await this.api.post('/auth/login', credentials);
    return response.data;
  }

  async logout(): Promise<void> {
    await this.api.post('/auth/logout');
    localStorage.removeItem('token');
    localStorage.removeItem('user');
  }

  // User endpoints
  async getUsers(): Promise<User[]> {
    const response: AxiosResponse<User[]> = await this.api.get('/users');
    return response.data;
  }

  async getUserById(id: number): Promise<User> {
    const response: AxiosResponse<User> = await this.api.get(`/users/${id}`);
    return response.data;
  }

  async createUser(user: Partial<User>): Promise<User> {
    const response: AxiosResponse<User> = await this.api.post('/users', user);
    return response.data;
  }

  async updateUser(id: number, user: Partial<User>): Promise<User> {
    const response: AxiosResponse<User> = await this.api.put(`/users/${id}`, user);
    return response.data;
  }

  async deleteUser(id: number): Promise<void> {
    await this.api.delete(`/users/${id}`);
  }

  // Destination endpoints
  async getDestinations(): Promise<Destination[]> {
    const response: AxiosResponse<Destination[]> = await this.api.get('/destinations');
    return response.data;
  }

  async getActiveDestinations(): Promise<Destination[]> {
    const response: AxiosResponse<Destination[]> = await this.api.get('/destinations/active');
    return response.data;
  }

  async getDestinationById(id: number): Promise<Destination> {
    const response: AxiosResponse<Destination> = await this.api.get(`/destinations/${id}`);
    return response.data;
  }

  async createDestination(destination: Partial<Destination>): Promise<Destination> {
    const response: AxiosResponse<Destination> = await this.api.post('/destinations', destination);
    return response.data;
  }

  async updateDestination(id: number, destination: Partial<Destination>): Promise<Destination> {
    const response: AxiosResponse<Destination> = await this.api.put(`/destinations/${id}`, destination);
    return response.data;
  }

  async deleteDestination(id: number): Promise<void> {
    await this.api.delete(`/destinations/${id}`);
  }

  // Tour endpoints
  async getTours(): Promise<Tour[]> {
    const response: AxiosResponse<Tour[]> = await this.api.get('/tours');
    return response.data;
  }

  async getAvailableTours(): Promise<Tour[]> {
    const response: AxiosResponse<Tour[]> = await this.api.get('/tours/available');
    return response.data;
  }

  async getTourById(id: number): Promise<Tour> {
    const response: AxiosResponse<Tour> = await this.api.get(`/tours/${id}`);
    return response.data;
  }

  async searchTours(filters: SearchFilters): Promise<Tour[]> {
    const response: AxiosResponse<Tour[]> = await this.api.get('/tours/search', { params: filters });
    return response.data;
  }

  async createTour(tour: Partial<Tour>): Promise<Tour> {
    const response: AxiosResponse<Tour> = await this.api.post('/tours', tour);
    return response.data;
  }

  async updateTour(id: number, tour: Partial<Tour>): Promise<Tour> {
    const response: AxiosResponse<Tour> = await this.api.put(`/tours/${id}`, tour);
    return response.data;
  }

  async deleteTour(id: number): Promise<void> {
    await this.api.delete(`/tours/${id}`);
  }

  // Reservation endpoints
  async getReservations(): Promise<Reservation[]> {
    const response: AxiosResponse<Reservation[]> = await this.api.get('/reservations');
    return response.data;
  }

  async getReservationById(id: number): Promise<Reservation> {
    const response: AxiosResponse<Reservation> = await this.api.get(`/reservations/${id}`);
    return response.data;
  }

  async createReservation(reservation: CreateReservationRequest): Promise<Reservation> {
    const response: AxiosResponse<Reservation> = await this.api.post('/reservations', reservation);
    return response.data;
  }

  async updateReservation(id: number, reservation: Partial<Reservation>): Promise<Reservation> {
    const response: AxiosResponse<Reservation> = await this.api.put(`/reservations/${id}`, reservation);
    return response.data;
  }

  async deleteReservation(id: number): Promise<void> {
    await this.api.delete(`/reservations/${id}`);
  }

  async confirmReservation(id: number): Promise<void> {
    await this.api.patch(`/reservations/${id}/confirm`);
  }

  async cancelReservation(id: number): Promise<void> {
    await this.api.patch(`/reservations/${id}/cancel`);
  }

  // Batch endpoints
  async exportUserData(): Promise<string> {
    const response: AxiosResponse<string> = await this.api.post('/batch/export-users');
    return response.data;
  }
}

export const apiService = new ApiService();
export default apiService; 