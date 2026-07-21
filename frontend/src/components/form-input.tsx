import { useState } from "react";
import { Button, TextInput, View, Text } from "react-native";

interface FormProps {
  label: string;
  buttonText: string;
  onSubmit: (value: string) => void; //Callback function
}

function Form({ label, buttonText, onSubmit }: FormProps) {
  const [value, setValue] = useState("");
  return (
    <View>
      <Text>{label}</Text>
      <TextInput value={value} onChangeText={setValue}></TextInput>
      <Button title={buttonText} onPress={() => onSubmit(value)}></Button>
    </View>
  );
}

export default Form;
