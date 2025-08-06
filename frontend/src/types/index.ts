export interface User {
  id: number;
  username: string;
  email: string;
  fullName: string;
  phoneNumber?: string;
  role: 'ADMIN' | 'USER' | 'STAFF';
  enabled: boolean;
  createdAt: string;
  updatedAt: string;
}

export interface Destination {
  id: number;
  name: string;
  description: string;
  country: string;
  city?: string;
  region?: string;
  type: 'CITY' | 'COUNTRY' | 'REGION' | 'RESORT' | 'LANDMARK';
  active: boolean;
  imageUrl?: string;
  createdAt: string;
  updatedAt: string;
}

export interface Tour {
  id: number;
  name: string;
  description: string;
  destination: Destination;
  price: number;
  duration: number;
  maxCapacity: number;
  currentCapacity: number;
  startDate: string;
  endDate: string;
  type: 'GROUP' | 'PRIVATE' | 'CUSTOM';
  status: 'AVAILABLE' | 'FULL' | 'CANCELLED' | 'COMPLETED';
  imageUrl?: string;
  createdAt: string;
  updatedAt: string;
}

export interface Reservation {
  id: number;
  user: User;
  tour: Tour;
  numberOfPeople: number;
  totalPrice: number;
  status: 'PENDING' | 'CONFIRMED' | 'CANCELLED' | 'COMPLETED';
  paymentStatus: 'PENDING' | 'PAID' | 'REFUNDED' | 'FAILED';
  specialRequests?: string;
  contactPhone?: string;
  contactEmail?: string;
  createdAt: string;
  updatedAt: string;
}

export interface ApiResponse<T> {
  data: T;
  message?: string;
  success: boolean;
}

export interface PaginatedResponse<T> {
  content: T[];
  totalElements: number;
  totalPages: number;
  size: number;
  number: number;
  first: boolean;
  last: boolean;
}

export interface LoginRequest {
  username: string;
  password: string;
}

export interface LoginResponse {
  token: string;
  user: User;
}

export interface CreateReservationRequest {
  tourId: number;
  numberOfPeople: number;
  specialRequests?: string;
  contactPhone?: string;
  contactEmail?: string;
}

export interface SearchFilters {
  destination?: string;
  minPrice?: number;
  maxPrice?: number;
  startDate?: string;
  endDate?: string;
  type?: string;
  status?: string;
} 