import { FC } from "react";
import { Link } from "react-router-dom";
import { Facebook, Twitter, Instagram } from "lucide-react";

const Footer: FC = () => {
  return (
    <footer className="bg-gray-800 text-gray-100 py-2">
      <div className="max-w-[1200px] mx-auto flex flex-col md:flex-row justify-between items-center">
        <div className="mb-6 md:mb-0">
          <img
            src="/src/imgs/logo.jpg"
            alt="Company Logo"
            className="w-20 mb-4 rounded-xl"
          />
          <p className="text-sm">
            OBSS TECH <br />
            Pendik Istanbul, Turkey
          </p>
          <p className="text-sm">© 2024 OBSS Corporation</p>
        </div>

        <div className="mb-6 md:mb-0">
          <h4 className="text-lg font-semibold mb-2">Quick Links</h4>
          <ul className="space-y-2 text-sm">
            <li>
              <Link to="/" className="hover:underline">
                Home
              </Link>
            </li>
            <li>
              <Link to="/products" className="hover:underline">
                Products
              </Link>
            </li>
            <li>
              <Link to="/about" className="hover:underline">
                About Us
              </Link>
            </li>
            <li>
              <Link to="/contact" className="hover:underline">
                Contact
              </Link>
            </li>
          </ul>
        </div>

        <div>
          <h4 className="text-lg font-semibold mb-2">Follow Us</h4>
          <div className="flex space-x-4">
            <a href="https://facebook.com" target="_blank" rel="noreferrer">
              <Facebook className="w-5 h-5 hover:text-blue-500" />
            </a>
            <a href="https://twitter.com" target="_blank" rel="noreferrer">
              <Twitter className="w-5 h-5 hover:text-blue-400" />
            </a>
            <a href="https://instagram.com" target="_blank" rel="noreferrer">
              <Instagram className="w-5 h-5 hover:text-pink-500" />
            </a>
          </div>
        </div>
      </div>
    </footer>
  );
};

export default Footer;
