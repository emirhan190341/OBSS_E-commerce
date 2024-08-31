import axios from "axios";
import toast from "react-hot-toast";
import { create } from "zustand";

const API_URL =
  import.meta.env.MODE === "development" ? "http://localhost:8080" : "";

axios.defaults.withCredentials = true;

export const useFavoriteListStore = create((set) => ({
  error: null,
  isLoading: false,
  favoriteListResponse: null,
  products: [],


  addFavorite: async (userId, productId) => {
    set({ isLoading: true, error: null });
    try {
      const response = await axios.post(
        `${API_URL}/api/v1/favoritelist/add`,
        null,
        {
          params: {
            userId,
            productId,
          },
        }
      );
      set({
        favoriteListResponse: response.data.response,
        isLoading: false,
      });
      toast.success("Product added to favorites");
      console.log("Product added to favorites:", response.data.response);
    } catch (error) {
      set({
        error: "Failed to add product to favorites. Please try again later.",
        isLoading: false,
      });
    }
  },
  getFavoriteProducts: async (userId) => {
    set({ isLoading: true, error: null });
    try {
      const response = await axios.get(
        `${API_URL}/api/v1/favoritelist/user/${userId}`
      );
      set({
        products: response.data.response,
        isLoading: false,
      });
      console.log("Favorite products retrieved:", response.data.response);
    } catch (error) {
      set({
        error: "Failed to fetch favorite products. Please try again later.",
        isLoading: false,
      });
    }
  },
  deleteFavorite: async (userId, productId) => {
    set({ isLoading: true, error: null });
    try {
      const response = await axios.delete(
        `${API_URL}/api/v1/favoritelist/remove`,
        {
          params: {
            userId,
            productId,
          },
        }
      );
      set((state) => ({
        products: state.products.filter((product) => product.id !== productId),
        isLoading: false,
      }));
      toast.success("Product removed from favorites");
      console.log("Product removed from favorites:", response.data.response);
    } catch (error) {
      set({
        error:
          "Failed to remove product from favorites. Please try again later.",
        isLoading: false,
      });
    }
  },
}));
