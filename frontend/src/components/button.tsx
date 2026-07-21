import { Text, Pressable } from "react-native";

interface ButtonProps {
  label: string;
  onPress: () => void;
  disabled?: boolean;
  variant?: "primary" | "secondary" | "danger";
}

const variants = {
  primary: "bg-orange-400",
  secondary: "border border-4 border-black bg-white",
  danger: "bg-red-600"
};

const textVariants = {
  primary: "text-white",
  secondary: "text-black",
  danger: "text-white"
};

function Button({
  label,
  onPress,
  disabled = false,
  variant = "primary"
}: ButtonProps) {
  return (
    <Pressable
      className={`rounded-md px-4 py-3 items-center ${variants[variant]}`}
      onPress={onPress}
      disabled={disabled}
    >
      <Text className={`text-3xl font-semibold ${textVariants[variant]}`}>
        {label}
      </Text>
    </Pressable>
  );
}

export default Button;
