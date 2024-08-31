import axios from "axios";
import { create } from "zustand";

const API_URL =
  import.meta.env.MODE === "development" ? "http://localhost:8080" : "";

axios.defaults.withCredentials = true;

interface Product {
  id: string;
  name: string;
  price: number;
  quantity: number;
  logo: string;
  sellerId: number;
  description: string;
  categories: string[];
}

interface ProductResponse {
  response: {
    content: Product[];
  };
}

interface SingleProductResponse {
  response: Product;
}

interface ProductStore {
  products: Product[];
  product?: Product;
  error: string | null;
  isLoading: boolean;
  isLastPage: boolean;

  getProducts: (pageNumber?: number, pageSize?: number) => Promise<void>;

  searchProducts: (
    keyword: string,
    pageNumber?: number,
    pageSize?: number
  ) => Promise<void>;

  getProductsSortedByDescPrice: (
    pageNumber?: number,
    pageSize?: number,
    sortType?: "desc" | "asc" | "createdAt"
  ) => Promise<void>;

  getProductsBySellerId: (
    sellerId: string,
    pageNumber?: number,
    pageSize?: number
  ) => Promise<void>;
  getProductById: (id: string) => Promise<void>;

  searchProductsWithoutBlacklisted: (
    userId: string,
    keyword: string,
    pageNumber?: number,
    pageSize?: number
  ) => Promise<void>;
}

export const useProductStore = create<ProductStore>((set) => ({
  products: [],
  product: undefined,
  error: null,
  isLoading: false,
  isLastPage: false,

  addNewProduct: async (product) => {
    set((state) => ({
      products: [...state.products, product],
    }));
  },

  removeProduct: async (sellerId) => {
    set((state) => ({
      products: state.products.filter(
        (product) => product.sellerId !== sellerId
      ),
    }));
  },

  dencrementProductQuantity: async (product: Product) => {
    set((state) => ({
      product:
        state.product && state.product.id === product.id
          ? { ...state.product, quantity: state.product.quantity - 1 }
          : state.product,
    }));
  },

  getProducts: async (pageNumber = 0, pageSize = 10) => {
    set({ isLoading: true, error: null });
    try {
      const response = await axios.get<ProductResponse>(
        `${API_URL}/api/v1/products`,
        {
          params: {
            pageNumber,
            pageSize,
          },
        }
      );
      set({
        products: response.data.response.content,
        isLoading: false,
        isLastPage:
          response.data.response.pageNumber ===
          response.data.response.totalPageCount - 1,
      });
      console.log(response.data.response);
    } catch (error: any) {
      set({
        error:
          error.message || "Failed to fetch products. Please try again later.",
        isLoading: false,
      });
    }
  },

  searchProducts: async (keyword = "", pageNumber = 0, pageSize = 10) => {
    set({ isLoading: true, error: null });
    try {
      const response = await axios.get<ProductResponse>(
        `${API_URL}/api/v1/products/search-all`,
        {
          params: {
            keyword,
            pageNumber,
            pageSize,
          },
        }
      );
      set({ products: response.data.response.content, isLoading: false });
      console.log(response.data.response);
    } catch (error) {
      set({
        error: "Failed to fetch products. Please try again later.",
        isLoading: false,
      });
    }
  },
  searchProductsWithoutBlacklisted: async (
    userId = "",
    keyword = "",
    pageNumber = 0,
    pageSize = 10
  ) => {
    set({ isLoading: true, error: null });
    try {
      const response = await axios.get<ProductResponse>(
        `${API_URL}/api/v1/products/search`,
        {
          params: {
            userId,
            keyword,
            pageNumber,
            pageSize,
          },
        }
      );
      set({
        products: response.data.response.content,
        isLoading: false,
        isLastPage:
          response.data.response.pageNumber ===
          response.data.response.totalPageCount - 1,
      });
      console.log(response.data.response);
    } catch (error) {
      set({
        error: "Failed to fetch products. Please try again later.",
        isLoading: false,
      });
    }
  },

  getProductsSortedByDescPrice: async (
    pageNumber = 0,
    pageSize = 10,
    sortType = "desc"
  ) => {
    set({ isLoading: true, error: null });
    try {
      const sortPath =
        sortType === "createdAt"
          ? "createdAt/desc"
          : sortType === "desc"
          ? "price/desc"
          : "price/asc";

      const response = await axios.get<ProductResponse>(
        `${API_URL}/api/v1/products/${sortPath}`,
        {
          params: {
            pageNumber,
            pageSize,
          },
        }
      );
      set({ products: response.data.response.content, isLoading: false });
    } catch (error) {
      set({
        error:
          "Failed to fetch products sorted by price. Please try again later.",
        isLoading: false,
      });
    }
  },

  getProductsBySellerId: async (sellerId, pageNumber = 0, pageSize = 10) => {
    set({ isLoading: true, error: null });
    try {
      const response = await axios.get<ProductResponse>(
        `${API_URL}/api/v1/products/seller/${sellerId}`,
        {
          params: {
            pageNumber,
            pageSize,
          },
        }
      );
      set({ products: response.data.response.content, isLoading: false });
    } catch (error) {
      set({
        error: "Failed to fetch products. Please try again later.",
        isLoading: false,
      });
    }
  },

  getProductById: async (id) => {
    set({ isLoading: true, error: null });
    try {
      const response = await axios.get<SingleProductResponse>(
        `${API_URL}/api/v1/products/${id}`
      );
      set({ product: response.data.response, isLoading: false });
    } catch (error) {
      set({
        error: "Failed to fetch product. Please try again later.",
        isLoading: false,
      });
    }
  },
}));
