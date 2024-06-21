package org.example.processor;

import org.example.entity.ExtraEntity;
import org.example.entity.ProductEntity;
import org.example.model.Extra;
import org.example.model.ExtraItem;
import org.example.model.OrderList;
import org.example.model.Product;
import org.example.model.ProductItem;
import org.example.model.Receipt;
import org.example.model.Type;
import org.example.repository.ExtraRepository;
import org.example.repository.ProductRepository;

import java.math.BigDecimal;
import java.util.List;


/**
 * The {@code ProductsAndExtrasBaseOrderProcessor} class implements the {@link OrderProcessor} interface and
 * processes an {@link OrderList} into a {@link Receipt}.
 * It retrieves product and extra information from the corresponding repositories to create detailed product and extra items.
 */
public class ProductsAndExtrasBaseOrderProcessor implements OrderProcessor {
    private final ProductRepository productRepository;
    private final ExtraRepository extraRepository;

    public ProductsAndExtrasBaseOrderProcessor(ProductRepository productRepository,
                                               ExtraRepository extraRepository) {
        this.productRepository = productRepository;
        this.extraRepository = extraRepository;
    }

    /**
     * Processes the provided {@link OrderList} and generates a {@link Receipt}.
     * This method transforms the list of products in the order into detailed product items, each containing
     * their respective extras.
     *
     * @param order the {@link OrderList} to be processed.
     * @return a {@link Receipt} containing the processed product items and a total price of zero (as a placeholder).
     */
    @Override
    public Receipt processOrder(OrderList order) {
        List<ProductItem> productItems = order.products().stream()
                .map(this::createProductItem)
                .toList();
        return new Receipt(productItems, BigDecimal.ZERO);
    }

    private ProductItem createProductItem(Product product) {
        // todo: can be optimized to not load each item 1-by-1
        ProductEntity productEntity = productRepository.findByName(product.name());
        List<ExtraItem> extraItems = product.extras().stream()
                .map(this::createExtraItem)
                .toList();

        return new ProductItem(product.name(),
                Type.valueOf(productEntity.type().name()),
                productEntity.price(),
                BigDecimal.ZERO,
                extraItems);
    }

    private ExtraItem createExtraItem(Extra extra) {
        // todo: can be optimized to not load each item 1-by-1
        ExtraEntity extraEntity = extraRepository.findByName(extra.name());
        return new ExtraItem(extra.name(), extraEntity.price(), BigDecimal.ZERO);
    }
}
