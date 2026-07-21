import { Button, View } from "react-native";

export default function Index() {
  return (
    <View className='w-full h-full flex items-center justify-center gap-y-4 bg-slate-400'>
      <Button
        title='Create Room'
        onPress={() => console.log("change to username form page")}
      />
      <Button
        title='Join Room'
        onPress={() =>
          console.log("change to room code form page then username form page")
        }
      />
    </View>
  );
}
