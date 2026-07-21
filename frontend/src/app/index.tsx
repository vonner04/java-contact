import { View } from "react-native";
import Button from "@/components/button";

export default function Index() {
  return (
    <View className='w-full h-full flex items-center justify-center bg-slate-400'>
      <div className='flex flex-col gap-y-4'>
        <Button
          label='Create Room'
          onPress={() => console.log("change to username form page")}
        />
        <Button
          label='Join Room'
          onPress={() =>
            console.log("change to room code form page then username form page")
          }
          variant='secondary'
        />
      </div>
    </View>
  );
}
