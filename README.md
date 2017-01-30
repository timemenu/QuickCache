# QuickCache
android image cache library

인터페이스 QuickCache
* putBitmap(key(String), bitmap(Bitmap))
* putBitmap(key(String), file(File))
* getBitmap(key(String))
* clear()

-- onCreate 등 초기화 부분
1. 메모리캐시
QuickCacheGenerator.getInstance().createMemoryCache(cacheName, cacheSize);
2. 디스크캐시
QuickCacheGenerator.getInstance().createDiskCache(context, cacheName, cacheSize);
3. 체인캐시(컨텍스트, 캐시이름, 캐시사이즈, 포맷, 품질)
QuickCacheGenerator.getInstance().createCainCache(context, cacheName, cacheSize, format, quality);

-- 이미지 캐시 사용 부분
QuickCache quickCache = QuickCacheGenerator.getInstance().getCache(cacheName);
Bitmap bitmap = quickCache.getBitmap(key);
if (bitmap != null) {
	imageView.set.....
}
-- 이미지 캐시 추가 부분
quickCache.putBitmap(key, someBitmap);
