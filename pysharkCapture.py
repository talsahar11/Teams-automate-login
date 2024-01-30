import pyshark
import os

def save_packet_to_file(packet, output_file):
    with open(output_file, 'a') as file:
        file.write(f"Packet #{packet.number}\n")
        file.write(f"Timestamp: {packet.sniff_timestamp}\n")
        file.write(f"Length: {packet.length} bytes\n")

        # Check if the packet contains an HTTP layer
        if 'http' in packet:
            http_layer = packet.http
            file.write(f"HTTP Layer:\n")
            file.write(f"  Method: {http_layer.request_method}\n")
            file.write(f"  URI: {http_layer.request_uri}\n")
            file.write(f"  Host: {http_layer.host}\n")
            file.write(f"  Content Type: {http_layer.get_field('content_type')}\n")

        file.write("\nDetails:\n")
        file.write(str(packet))
        file.write("\n" + "=" * 40 + "\n")

def live_capture_and_save(interface, output_file):
    try:
        capture = pyshark.LiveCapture(interface=interface, display_filter="json")
        for packet in capture.sniff_continuously():  # Adjust packet_count as needed
            save_packet_to_file(packet, output_file)
    except KeyboardInterrupt:
        print("Capture stopped by the user.")
    except Exception as e:
        print(f"An error occurred: {str(e)}")

if __name__ == "__main__":
    interface_to_capture = "wlx2cd05a2aaa7c"  # Replace with your network interface, e.g., "eth0" or "wlan0"
    output_file = "captured_packets.txt"  # Replace with the desired output file path

    live_capture_and_save(interface_to_capture, output_file)
