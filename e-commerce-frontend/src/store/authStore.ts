import { create } from "zustand";
import axios from "axios";
import toast from "react-hot-toast";

const API_URL =
  import.meta.env.MODE === "development"
    ? "http://localhost:8080"
    : "/api/auth";

axios.defaults.withCredentials = true;

interface User {
  id: number;
  username: string;
  fullName: string;
  email: string;
  phoneNumber?: string;
  address?: string;
  isAdmin: boolean;
  listOfFavoriteProducts: string[];
  listOfBlacklistedSellers: number[];
}

interface AuthStore {
  user: User | null;
  isAuthenticated: boolean;
  error: string | null;
  isLoading: boolean;
  isCheckingAuth: boolean;
  message: string | null;

  setUserFavoriteProducts: (productId: string) => void;
  signup: (
    username: string,
    password: string,
    name: string,
    surname: string,
    email: string,
    phoneNumber?: string,
    address?: string
  ) => Promise<void>;
  login: (formData: LoginFormData) => Promise<void>;
  logout: () => Promise<void>;
  checkAuth: () => Promise<void>;
}

interface LoginFormData {
  username: string;
  password: string;
}

export const useAuthStore = create<AuthStore>((set) => ({
  user: null,
  isAuthenticated: false,
  error: null,
  isLoading: false,
  isCheckingAuth: true,
  message: null,

  setBlacklistSellers: (sellerId: number) => {
    set((state) => {
      const currentUser = state.user;
      if (!currentUser) return state;

      const isSellerBlacklisted =
        currentUser.listOfBlacklistedSellers.includes(sellerId);

      return {
        user: {
          ...currentUser,
          listOfBlacklistedSellers: isSellerBlacklisted
            ? currentUser.listOfBlacklistedSellers.filter(
                (id) => id !== sellerId
              ) // Remove seller ID
            : [...currentUser.listOfBlacklistedSellers, sellerId], // Add seller ID
        },
      };
    });
  },

  setUserFavoriteProducts: (productId: string) => {
    set((state) => {
      const currentUser = state.user;
      if (!currentUser) return state;

      const isProductFavorite =
        currentUser.listOfFavoriteProducts.includes(productId);

      return {
        user: {
          ...currentUser,
          listOfFavoriteProducts: isProductFavorite
            ? currentUser.listOfFavoriteProducts.filter(
                (id) => id !== productId
              ) // Remove product ID
            : [...currentUser.listOfFavoriteProducts, productId], // Add product ID
        },
      };
    });
  },

  signup: async (
    username: string,
    password: string,
    name: string,
    surname: string,
    email: string,
    phoneNumber?: string,
    address?: string
  ) => {
    set({ isLoading: true, error: null });
    try {
      const response = await axios.post(`${API_URL}/api/v1/users/save`, {
        username,
        password,
        name,
        surname,
        email,
        phoneNumber,
        address,
      });
      set({
        user: response.data.user,
        isAuthenticated: true,
        isLoading: false,
      });
    } catch (error: any) {
      set({
        error: error.response?.data?.message || "Error signing up",
        isLoading: false,
      });
      throw error;
    }
  },

  login: async (formData: LoginFormData) => {
    set({ isLoading: true, error: null });
    try {
      const response = await axios.post(`${API_URL}/api/v1/auth/login`, formData);
      set({
        isAuthenticated: true,
        user: response.data.user,
        error: null,
        isLoading: false,
      });
      console.log(response.data);
      toast.success("Login successful!");
    } catch (error: any) {
      set({
        error: error.response?.data?.message || "Error logging in",
        isLoading: false,
      });
      toast.error("Login failed. Please check your details and try again.");
      throw error;
    }
  },

  logout: async () => {
    set({ isLoading: true, error: null });
    try {
      await axios.post(`${API_URL}/logout`);
      set({
        user: null,
        isAuthenticated: false,
        error: null,
        isLoading: false,
      });
    } catch (error: any) {
      set({ error: "Error logging out", isLoading: false });
      throw error;
    }
  },

  checkAuth: async () => {
    set({ isCheckingAuth: true, error: null });
    try {
      const response = await axios.get(`${API_URL}/api/v1/users/auth-user`);
      set({
        user: response.data.response,
        isAuthenticated: true,
        isCheckingAuth: false,
      });
      console.log(response.data);
    } catch (error: any) {
      set({ error: null, isCheckingAuth: false, isAuthenticated: false });
    }
  },
}));
