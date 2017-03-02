///////////////////////////////////////////////////////////////////////////////
//                   ALL STUDENTS COMPLETE THESE SECTIONS
// Title:            program2
// Files:            Receiver.java
//					 PacketLinkedList.java
//					 PacketLinkedListIterator.java
//					 BadImageContentException.java
//					 BadImageHeaderException.java
// Semester:         367 Spring 2016
//
// Author:           Qiannan Guo
// Email:            qguo43@wisc.edu
// CS Login:         qiannan
// Lecturer's Name:  Jim Skrentny
//
///////////////////////////////////////////////////////////////////////////////
import java.io.IOException;

/**
 * The main class. It simulates a application (image viewer) receiver by
 * maintaining a list buffer. It collects packets from the queue of 
 * InputDriver and arrange them properly, and then reconstructs the image
 * file from its list buffer.
 */
public class Receiver {
	private InputDriver input;
	private ImageDriver img;
	private PacketLinkedList<SimplePacket> list;

	/**
	 * Constructs a Receiver to obtain the image file transmitted.
	 * @param file the filename you want to receive
	 */
	public Receiver(String file) {
		try {
			input = new InputDriver(file, true);
		} catch (IOException e) {
			System.out.println(
					"The file, " + file + ", isn't existed on the server.");
			System.exit(0);
		}
		img = new ImageDriver(input);
		// TODO: properly initialize your field
		list = new PacketLinkedList<SimplePacket> ();
	}

	/**
	 * Returns the PacketLinkedList buffer in the receiver
	 * 
	 * @return the PacketLinkedList object
	 */
	public PacketLinkedList<SimplePacket> getListBuffer() {
		return list;
	}

	/**
	 * Asks for retransmitting the packet. The new packet with the sequence 
	 * number will arrive later by using {@link #askForNextPacket()}.
	 * Notice that ONLY packet with invalid checksum will be retransmitted.
	 * 
	 * @param pkt the packet with bad checksum
	 * @return true if the requested packet is added in the receiving queue; otherwise, false
	 */
	public boolean askForRetransmit(SimplePacket pkt) {
		return input.resendPacket(pkt);
	}


	/**
	 * Asks for retransmitting the packet with a sequence number. 
	 * The requested packet will arrive later by using 
	 * {@link #askForNextPacket()}. Notice that ONLY missing
	 * packet will be retransmitted. Pass seq=0 if the missing packet is the
	 * "End of Streaming Notification" packet.
	 * 
	 * @param seq the sequence number of the requested missing packet
	 * @return true if the requested packet is added in the receiving queue; otherwise, false
	 */
	public boolean askForMissingPacket(int seq) {
		return input.resendMissingPacket(seq);
	}

	/**
	 * Returns the next packet.
	 * 
	 * @return the next SimplePacket object; returns null if no more packet to
	 *         receive
	 */
	public SimplePacket askForNextPacket() {
		return input.getNextPacket();
	}

	/**
	 * Returns true if the maintained list buffer has a valid image content. Notice
	 * that when it returns false, the image buffer could either has a bad
	 * header, or just bad body, or both.
	 * 
	 * @return true if the maintained list buffer has a valid image content;
	 *         otherwise, false
	 */
	public boolean validImageContent() {
		return input.validFile(list);
	}

	/**
	 * Returns if the maintained list buffer has a valid image header
	 * 
	 * @return true if the maintained list buffer has a valid image header;
	 *         otherwise, false
	 */
	public boolean validImageHeader() {
		return input.validHeader(list.get(0));
	}

	/**
	 * Outputs the formatted content in the PacketLinkedList buffer. See
	 * course webpage for the formatting detail.
	 */
	public void displayList() {
		// TODO: implement this method firstly to help you debug
		PacketLinkedListIterator <SimplePacket> itrr = list.iterator();
		SimplePacket sth;
		while(itrr.hasNext()){
			sth = itrr.next();
			if (itrr.hasNext()) {
				if (sth.isValidCheckSum()) {
					System.out.print(sth.getSeq()+", ");
				}
				else {
					System.out.print("[" + sth.getSeq()+ "], ");
				}
			}
			else {
				if (sth.isValidCheckSum()) {
					System.out.print(sth.getSeq()+" ");
				}
				else {
					System.out.print("[" + sth.getSeq()+ "] ");
				}
			}
		}
	}

	/**
	 * Reconstructs the file by arranging the {@link PacketLinkedList} in
	 * correct order. It uses {@link #askForNextPacket()} to get packets until
	 * no more packet to receive. It eliminates the duplicate packets and asks
	 * for retransmitting when getting a packet with invalid checksum.
	 */
	public void reconstructFile() {
		// TODO: Collect the packets and arrange them in order.
		// 		 You can try to collect all packets and put them into your list
		//       without any processing and use implemented displayList() to see
		//       the pattern of packets you are going to receive.
		//       Then, properly handle the invalid checksum and duplicates. The 
		//       first image file, "secret0.jpg", would not result in missing
		//       packets into your receiving queue such that you can test it once
		//       you get the first two processing done.
		//

		// TODO: Processing missing packets for the other four images. You should
		//       utilize the information provided by "End of Streaming Notification
		//       Packet" though this special packet could be lost while transmitting.

		//
		
		/**
		 * This is used for debugging. It gets all the Packets from the queue and add it to 
		 * the list. The method displayList() is used for printing all the contents in the
		 * List, separated by commas. Duplicates are also shown if there are duplicated packets
		 * in the queue. Packets that are corrupted are indicated by square brackets []. 
		 */
//		SimplePacket temp = askForNextPacket();
//		int i = 0;
//		while(temp!=null){
//			list.add(temp);
//			temp = askForNextPacket();
//			i ++;
//			displayList();
//			System.out.println();
//		}
//		while (i!=0) {
//			list.remove(i-1);
//			i--;
//		}
//		System.out.println("The list is: ");
//		displayList();
//		System.out.println("Going to work =]");
		//a temperate packet
		SimplePacket tmp = askForNextPacket();
		//total number of Packet that shown as negative in End Of Streaming Notification
		int totalNum = 0;
		boolean noEnd = true;
		//there are still packets in the queue
		while(tmp!=null){
			//check if CheckSum is true and the Packet is not End of Streaming Notification
			if(tmp.isValidCheckSum() && tmp.getSeq()>0){
				PacketLinkedListIterator<SimplePacket> itr = list.iterator();
				SimplePacket sth;
				int count = 0;
				//check whether the tmp is added in the list
				boolean added = false;
				while(itr.hasNext() && !added){
					sth = itr.next();
					//for duplicated packets
					if(sth.getSeq()==tmp.getSeq()){
						list.add(count, tmp);
						list.remove(count+1);
						added = true;
					}
					//adding packets in order
					else if(sth.getSeq()>tmp.getSeq()){
						list.add(count, tmp);
						added = true;
					}
					count++;
				}
				if(!added){
					list.add(tmp);
				}
			}
			//if the Packet is corrupt
			else if(!tmp.isValidCheckSum() && tmp.getSeq()>0){
				boolean a;
				do{
					a = askForRetransmit(tmp);
				}while(!a);
			}
			//if the packet is End of Streaming Notification
			else if(tmp.isValidCheckSum() && tmp.getSeq()<0){
				totalNum = -tmp.getSeq();
				noEnd = false;
			}
			tmp = askForNextPacket();
		}
		//if there is End of Streaming Notification
		if(noEnd){
			boolean c = false;
			while(!c){
				c = askForMissingPacket(0);
				tmp = askForNextPacket();

				noEnd = false;
			}
			totalNum = -tmp.getSeq();
		}
		//check whether there are some packets missing
		if(totalNum != list.size()){
			PacketLinkedListIterator<SimplePacket> itr2 = list.iterator();
			int number = 1;
			SimplePacket sth2 = itr2.next();
			while(list.size()==0 || itr2.hasNext()){
				if(list.size()!=0 && sth2.getSeq()==number){
					number++;
					sth2 = itr2.next();
				}
				else{
					boolean d;
					do {
						d = askForMissingPacket(number);
					}while(!d);
					boolean b = false;
					boolean z;
					while(!b){
						SimplePacket lala = askForNextPacket();
						if(lala.isValidCheckSum()){
							list.add(number-1,lala);
							number++;
							b = true;
						}
						else if (!lala.isValidCheckSum()) {
							do {
								z = askForRetransmit(lala);
							}while(!z);
						}
					}
				}
			}
		}
	}


	/**
	 * Opens the image file by merging the content in the maintained list
	 * buffer.
	 */
	public void openImage() {	
		try {
			img.openImage(list);
		} 
		// TODO: catch the image-related exception
		/* throws BadImageHeaderException if the maintained list buffer has an 
		 * invalid image header, throws BadImageContentException if the 
		 * maintained list buffer has an invalid image content*/
		catch(BrokenImageException e){
			if(!this.validImageContent()){
				throw new BadImageContentException("The image is broken due to corrupt content");
			}
			else if(!this.validImageHeader()){
				throw new BadImageHeaderException("The image is broken due to a damaged header");
			}
		}
		catch (Exception e) {
			System.out.println(
					"Please catch the proper Image-related Exception.");
			e.printStackTrace();
		}
	}

	/**
	 * Initiates a Receiver to reconstruct collected packets and open the Image
	 * file, which is specified by args[0]. 
	 * 
	 * @param args command line arguments
	 */
	public static void main(String[] args) {
		if (args.length != 1) {
			System.out.println("Usage: java Receiver [filename_on_server]");
			return;
		}
		Receiver recv = new Receiver(args[0]);
		recv.reconstructFile();
		//recv.displayList(); //use for debugging
		recv.openImage();
	}
}
