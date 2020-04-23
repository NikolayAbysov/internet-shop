package mate.academy.internetshop.service.impl;

import mate.academy.internetshop.dao.BucketDao;
import mate.academy.internetshop.lib.anno.Inject;
import mate.academy.internetshop.lib.anno.Service;
import mate.academy.internetshop.model.Bucket;
import mate.academy.internetshop.model.Product;
import mate.academy.internetshop.service.BucketService;
import mate.academy.internetshop.service.ProductService;

@Service
public class BucketServiceImpl implements BucketService {

    @Inject
    private BucketDao bucketDao;
    @Inject
    private ProductService productDao;

    @Override
    public Bucket addItem(Long bucketId, Long itemId) {
        Bucket bucket = bucketDao.get(bucketId);
        Product product = productDao.get(itemId);
        bucket.getProducts().add(product);
        return bucketDao.update(bucket);
    }

    @Override
    public void getItem() {
    }

    @Override
    public void updateItem() {
    }

    @Override
    public void deleteItem() {
    }
}
