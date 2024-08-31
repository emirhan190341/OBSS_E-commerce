import { XIcon } from "lucide-react";
import React from "react";
import { useUserStore } from "../../store/userStore";

interface User {
  id: number;
  username: string;
  fullName: string;
  email: string;
  phoneNumber: string;
  address: string;
}

interface UserCardProps {
  user: User;
}

const UserCard: React.FC<UserCardProps> = ({ user }) => {
  const { users, deleteUser } = useUserStore();

  const handleDelete = (userId: number) => {
    deleteUser(userId);
  };

  return (
    <div className="bg-white shadow-md p-4 rounded-lg relative">

     {
      user.id !== 1 && (
        <button
          onClick={() => handleDelete(user.id)}
          className="absolute top-2 right-2"
        >
          <XIcon className="w-5  text-red-500" />
        </button>
      )
     }

      <h1 className="text-lg font-semibold">{user.fullName}</h1>
      <p className="text-sm text-gray-500">{user.username}</p>
      <p className="text-sm text-gray-500">{user.email}</p>
      <p className="text-sm text-gray-500">{user.phoneNumber}</p>
      <p className="text-sm text-gray-500">{user.address}</p>
    </div>
  );
};

export default UserCard;
