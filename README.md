# QuickCache
android image cache library

인터페이스 QuickCache
* putBitmap(key(String), bitmap(Bitmap))
* putBitmap(key(String), file(File))
* getBitmap(key(String))
* clear()

-- onCreate 등 초기화 부분

// 2레벨 캐시(이미지 파일 캐시)를 사용하려면 동일 이름의 파일 캐시를 생성해 주어야 한다.
DiskCacheFactory.getInstance().create(cacheName, cacheSize, format, quality);
// 이미지 캐시 초기화
ImageCacheFactory.getInstance().createTwoLevelCache(cacheName, memoryImageMaxCounts);

-- 이미지 캐시 사용 부분
ImageCache imageCache = ImageCacheFactory.getInstance().getCache(cacheName);
Bitmap bitmap = imageCache.getBitmap(key);
if (bitmap != null) {
	imageView.set.....
}

-- 이미지 캐시 추가 부분
imageCache.putBitmap(key, someBitmap);
