const TransitionText = ({ children }: { children: ReactNode }) => {
  return (
    <div className="group font-bold transition duration-300">
      {children}
      <span className="block max-w-0 group-hover:max-w-full transition-all duration-500 h-0.5 bg-sky-600"></span>
    </div>
  );
};

export default TransitionText;
