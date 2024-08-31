import axios from "axios";
import toast from "react-hot-toast";
import { create } from "zustand";

// Define the Seller type
interface Seller {
  id: string; // Adjust according to your actual Seller ID type
  companyName: string;
  contactNumber?: string;
  logo?: string;
  email: string;
  websiteUrl?: string;
  address?: string;
  rating?: number;
  isVerified: boolean;
}

// Define the Zustand store state and methods
interface BlackListStore {
  error: string | null;
  isLoading: boolean;
  sellers: Seller[];
  addBlacklist: (userId: string, sellerId: string) => Promise<void>;
  getBlackList: (userId: string) => Promise<void>;
  deleteBlacklist: (userId: string, sellerId: string) => Promise<void>;
}

// Define the API URL based on the environment
const API_URL =
  import.meta.env.MODE === "development" ? "http://localhost:8080" : "";

// Ensure axios sends cookies with requests
axios.defaults.withCredentials = true;

// Create the Zustand store with TypeScript
export const useBlackListStore = create<BlackListStore>((set) => ({
  error: null,
  isLoading: false,
  sellers: [], // Initialize sellers as an empty array

  addBlacklist: async (userId: string, sellerId: string) => {
    set({ isLoading: true, error: null });
    try {
      const response = await axios.post(
        `${API_URL}/api/v1/blacklist/add`,
        null,
        {
          params: {
            userId,
            sellerId,
          },
        }
      );
      set({ isLoading: false });
      toast.success("Seller added to blacklist");
      console.log("seller added to blacklist:", response.data.response);
    } catch (error: any) {
      set({
        error: "Failed to add seller to blacklist. Please try again later.",
        isLoading: false,
      });
    }
  },

  getBlackList: async (userId: string) => {
    set({ isLoading: true, error: null });
    try {
      const response = await axios.get(`${API_URL}/api/v1/blacklist`, {
        params: { userId },
      });
      set({
        sellers: response.data.response || [],
        isLoading: false,
      });
      console.log("blacklist:", response.data.response);
    } catch (error: any) {
      set({
        error: "Failed to fetch blacklist. Please try again later.",
        isLoading: false,
      });
    }
  },

  deleteBlacklist: async (userId: string, sellerId: string) => {
    set({ isLoading: true, error: null });
    try {
      const response = await axios.delete(
        `${API_URL}/api/v1/blacklist/remove`,
        {
          params: {
            userId,
            sellerId,
          },
        }
      );
      set((state) => ({
        sellers: state.sellers.filter((seller) => seller.id !== sellerId),
        isLoading: false,
      }));
      toast.success("Seller removed from blacklist");
      console.log("seller removed from blacklist:", response.data.response);
    } catch (error: any) {
      set({
        error:
          "Failed to remove seller from blacklist. Please try again later.",
        isLoading: false,
      });
    }
  },
}));
