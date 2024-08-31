import axios from "axios";
import toast from "react-hot-toast";
import { create } from "zustand";

const API_URL =
  import.meta.env.MODE === "development" ? "http://localhost:8080" : "";

axios.defaults.withCredentials = true;

export interface CartItem {
  id: string;
  productId: string;
  productName: string;
  quantity: number;
  price: number;
  imageUrl: string;
}

export interface CartResponse {
  response: {
    content: CartItem[];
  };
}

export interface CartStore {
  error: string | null;
  isLoading: boolean;
  cart: CartItem[] | null;
  addToCart: (
    userId: string,
    productId: string,
    quantity: number
  ) => Promise<void>;

  getCartByUserId: (
    userId: string,
    pageNumber?: number,
    pageSize?: number
  ) => Promise<void>;
  
  deleteCartItems: (userId: string) => Promise<void>;
}

export const useCartStore = create<CartStore>((set) => ({
  error: null,
  isLoading: false,
  cart: [],


  addToCart: async (userId, productId, quantity) => {
    set({ isLoading: true, error: null });
    try {
      const response = await axios.post(`${API_URL}/api/v1/carts/add`, null, {
        params: {
          userId,
          productId,
          quantity,
        },
      });
      set({
        isLoading: false,
      });
      toast.success("Product added to cart");
      console.log("Product added to cart:", response.data.response);
    } catch (error) {
      set({
        error: "Failed to add product to cart. Please try again later.",
        isLoading: false,
      });
    }
  },

  getCartByUserId: async (userId, pageNumber = 0, pageSize = 10) => {
    set({ isLoading: true, error: null });
    try {
      const response = await axios.get<CartResponse>(
        `${API_URL}/api/v1/carts/get`,
        {
          params: {
            userId,
            pageNumber,
            pageSize,
          },
        }
      );
      set({
        cart: response.data.response.content,
        isLoading: false,
      });
      console.log(response.data.response);
    } catch (error) {
      set({
        error: "Failed to fetch cart items. Please try again later.",
        isLoading: false,
      });
    }
  },

  deleteCartItems: async (userId) => {
    set({ isLoading: true, error: null });
    try {
      await axios.delete(`${API_URL}/api/v1/carts/delete`, {
        params: {
          userId,
        },
      });
      set({
        cart: [],
        isLoading: false,
      });
      toast.success("Order created successfully");
    } catch (error) {
      set({
        error: "Failed to delete cart items. Please try again later.",
        isLoading: false,
      });
    }
  },
}));
