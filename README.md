P.S: The code is so dirty and might hurt yours eyes, read it on your own risks!

#[Elevator Simulation]
In a nutshell this is simulation of two elevators, one works as the queue of workers entering is a PriorityQueue the second works as its queue is a normal Queue (FIFO).
This program find the best case that will make the smallest average time workers have wait before going to their destination by generating all different cases (2^18 since we have 18 worker here) and calculate each time, the funniest part of the project is this, i will try to explain it in details in a different post.

# [Ants and bees]

The second part is a game of ants and bees trying to pass a path that has different obstacles and also fight each other to survive till the end .

# [Turkish Version]

# [Çok Asansörlü Sistemde Kuyruk Simülasyonu]
İş merkezi olan bir binanın iki adet asansörü bulunmaktadır. Sabahleyin gelen çalışanlar, asansörlerden birisine FIFO, diğerine ise PQ düzeninde (sırasında) binmektedirler. FIFO sırası ile kuyruk oluşturulan asansör 5 saniyede 1 kat çıkarken, PQ sırasındaki asansörün hızı 0.5 kat/sn.’dir. Şirketin kapısı 8:30’da açılmaktadır. Binanın 10 katlı olduğunu, bir asansör hangi hızla çıkıyorsa, o hızla indiğini, asansörlerin 4’er kişi alabildiğini, asansörün durduğu katlarda 4 sn. bekleme yapıldığını, tüm çalışanların yaklaşık aynı anda geldiklerini, sabahleyin diğer katlardan biniş olmadığını, zemin katın kat 0 olduğunu varsayabilirsiniz. Gelenlerin sırayla,

# [Karınca Sürüsü Hareketi ve Köprü Kurma]  
Karınca A, B, C, D, E sırayla S (Start) noktasından hedefe yani Goal G noktasına ilerleyecekler (Şekil 3). Üç adet çukur, yığıt şeklinde temsil ediliyor. Sırasıyla 3, 2 ve 4 karınca alacak boyuta sahipler. En önde ilerleyen karıncadan itibaren çukura düşüyorlar ve köprü kurarak sonrakilerin geçmesini sağlıyorlar. Sürü ilerlerken çukurdakiler sürünün hemen ardından peşlerine takılıp çıkıyorlar. Örnekte ilk çukurdan D, E önde, hemen arkalarından C, B, A sırasında çıkacaklar. Aralarda  şeklinde belirtilen tuzaklar olabilecek ve oraya ilk düşen karınca takılarak G noktasına ulaşamayacak.
Sağdan sola da her birinin sağlık durumu 3 olan 3 adet arı (X, Y, Z) ilerlesin. Karşılaştıklarında birbirlerinin sağlık durumlarını yiyebilsinler. Arılardan veya karıncalardan karşı tarafa geçenleri sırası ile yazdırınız. Yığıt ve tuzaklara arıların da girebildiklerini varsayın
