import axios from "axios";
import { create } from "zustand";

// Define the User interface
interface User {
  id: number;
  username: string;
  fullName: string;
  email: string;
  phoneNumber: string;
  address: string;
  isAdmin: boolean;
  listOfFavoriteProducts: string[];
}

// Define the Zustand store state and methods
interface UserStore {
  user: User | null;
  users: User[];
  isAuthenticated: boolean;
  error: string | null;
  isLoading: boolean;
  isCheckingAuth: boolean;
  message: string | null;
  isLastPage: boolean;
  addNewUser: (user: User) => void;
  getUserById: (userId: number) => Promise<void>;
  getUsers: (pageNumber?: number, pageSize?: number) => Promise<void>;
  searchUsers: (keyword: string) => Promise<void>;
  deleteUser: (userId: number) => Promise<void>;
}

// Define the API URL based on the environment
const API_URL =
  import.meta.env.MODE === "development"
    ? "http://localhost:8080"
    : "/api/auth";

// Ensure axios sends cookies with requests
axios.defaults.withCredentials = true;

// Create the Zustand store with TypeScript
export const useUserStore = create<UserStore>((set) => ({
  user: null,
  users: [],
  isAuthenticated: false,
  error: null,
  isLoading: false,
  isCheckingAuth: true,
  message: null,
  isLastPage: false,

  addNewUser: (user: User) => {
    set((state) => ({
      users: [...state.users, user],
    }));
  },

  
  getUserById: async (userId: number) => {
    set({ isLoading: true, error: null });
    try {
      const response = await axios.get<User>(
        `${API_URL}/api/v1/users/${userId}`
      );
      set({ user: response.data, isLoading: false });
    } catch (error) {
      set({
        error: "Failed to fetch user. Please try again later.",
        isLoading: false,
      });
    }
  },

  getUsers: async (pageNumber = 0, pageSize = 10) => {
    set({ isLoading: true, error: null });
    try {
      const response = await axios.get(`${API_URL}/api/v1/users`, {
        params: {
          pageNumber,
          pageSize,
        },
      });
      set({
        users: response.data.response.content,
        isLoading: false,
        isLastPage:
          response.data.response.pageNumber ===
          response.data.response.totalPageCount - 1,
      });
      console.log(response.data);
    } catch (error) {
      set({
        error: "Failed to fetch users. Please try again later.",
        isLoading: false,
      });
    }
  },

  searchUsers: async (keyword: string) => {
    set({ isLoading: true, error: null });
    try {
      const response = await axios.get(`${API_URL}/api/v1/users/search`, {
        params: {
          keyword,
        },
      });
      set({ users: response.data.response.content, isLoading: false });
      console.log(response.data);
    } catch (error) {
      set({
        error: "Failed to fetch users. Please try again later.",
        isLoading: false,
      });
    }
  },

  deleteUser: async (userId: number) => {
    set({ isLoading: true, error: null });
    try {
      await axios.delete(`${API_URL}/api/v1/users/${userId}`);
      set((state) => ({
        users: state.users.filter((user) => user.id !== userId),
        isLoading: false,
      }));
    } catch (error) {
      set({
        error: "Failed to delete user. Please try again later.",
        isLoading: false,
      });
    }
  },
}));
