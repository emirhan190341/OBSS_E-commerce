import { ReactNode } from "react";
import { cn } from "../../lib/utils";

const UnderlinedText = ({
  children,
  className,
}: {
  children: ReactNode;
  className?: string;
}) => {
  return (
    <span
      className={cn(
        "underline underline-offset-4 decoration-dashed decoration-sky-400",
        className
      )}
    >
      {children}
    </span>
  );
};
export default UnderlinedText;
