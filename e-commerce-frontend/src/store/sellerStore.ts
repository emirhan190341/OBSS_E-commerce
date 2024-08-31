import { create } from "zustand";
import axios from "axios";
import toast from "react-hot-toast";

// Define the Seller and API response types
interface Seller {
  id: number; // Long in Java maps to number in TypeScript
  companyName: string;
  contactNumber?: string; // Assuming it might not always be present
  logo?: string; // Assuming it might not always be present
  email: string;
  websiteUrl?: string; // Assuming it might not always be present
  address?: string; // Assuming it might not always be present
  rating?: number; // Assuming it might not always be present
  isVerified: boolean; // Defaults to false in Java but can be true or false
}

interface SellerResponse {
  content: Seller[];
  pageNumber: number;
  totalPageCount: number;
}

interface ApiResponse<T> {
  response: T;
}

interface SellerStore {
  sellers: Seller[];
  seller: Seller | null;
  error: string | null;
  isLoading: boolean;
  isLastPage: boolean;
  addNewSeller: (seller: Seller) => void;
  getSellers: (pageNumber?: number, pageSize?: number) => Promise<void>;
  searchSellers: (
    keyword: string,
    page?: number,
    size?: number
  ) => Promise<void>;
  getSellersSortedByDescRating: (
    pageNumber?: number,
    pageSize?: number
  ) => Promise<void>;
  getSellerById: (id: string) => Promise<void>;
}

const API_URL =
  import.meta.env.MODE === "development" ? "http://localhost:8080" : "";

// Ensure axios sends cookies with requests
axios.defaults.withCredentials = true;

// Create the Zustand store with TypeScript
export const useSellerStore = create<SellerStore>((set) => ({
  sellers: [],
  seller: null,
  error: null,
  isLoading: false,
  isLastPage: false,

  addNewSeller: (seller: Seller) => {
    set((state) => ({
      sellers: [...state.sellers, seller],
    }));
  },

  getSellers: async (pageNumber = 0, pageSize = 10) => {
    set({ isLoading: true, error: null });
    try {
      const response = await axios.get<ApiResponse<SellerResponse>>(
        `${API_URL}/api/v1/sellers`,
        {
          params: { pageNumber, pageSize },
        }
      );
      set({
        sellers: response.data.response.content,
        isLoading: false,
        isLastPage:
          response.data.response.pageNumber ===
          response.data.response.totalPageCount - 1,
      });
    } catch (error: any) {
      set({
        error: "Failed to fetch sellers. Please try again later.",
        isLoading: false,
      });
    }
  },

  searchSellers: async (keyword: string, page = 0, size = 3) => {
    set({ isLoading: true, error: null });
    try {
      const response = await axios.get<ApiResponse<SellerResponse>>(
        `${API_URL}/api/v1/sellers/search`,
        {
          params: { keyword, page, size },
        }
      );
      set({
        sellers: response.data.response.content,
        isLoading: false,
      });
      console.log("Fetched sellers:", response.data.response);
    } catch (error: any) {
      set({
        error: "Failed to fetch sellers. Please try again later.",
        isLoading: false,
      });
    }
  },

  getSellersSortedByDescRating: async (pageNumber = 0, pageSize = 10) => {
    set({ isLoading: true, error: null });
    try {
      const response = await axios.get<ApiResponse<SellerResponse>>(
        `${API_URL}/api/v1/sellers/rating`,
        {
          params: { pageNumber, pageSize },
        }
      );
      set({
        sellers: response.data.response.content,
        isLoading: false,
      });
    } catch (error: any) {
      set({
        error: "Failed to fetch sellers. Please try again later.",
        isLoading: false,
      });
    }
  },

  getSellerById: async (id: string) => {
    set({ isLoading: true, error: null });
    try {
      const response = await axios.get<ApiResponse<Seller>>(
        `${API_URL}/api/v1/sellers/${id}`
      );
      set({
        seller: response.data.response,
        isLoading: false,
      });
      console.log("Fetched seller:", response.data.response);
    } catch (error: any) {
      set({
        error: "Failed to fetch seller. Please try again later.",
        isLoading: false,
      });
      toast.error("Failed to fetch seller.");
    }
  },
  removeSeller: async (id: number) => {
    set({ isLoading: true, error: null });
    try {
      await axios.delete(`${API_URL}/api/v1/sellers/${id}`);
      set({
        sellers: [],
        isLoading: false,
      });
      toast.success("Seller deleted successfully");
    } catch (error: any) {
      set({
        error: "Failed to delete seller. Please try again later.",
        isLoading: false,
      });
      toast.error("Failed to delete seller.");
    }
  },
}));
