## 📖 Giới thiệu (Overview)

## 🚀 Công nghệ sử dụng (Tech Stack)
* **Ngôn ngữ:** Java
* **Framework:** Spring Boot
* **Quản lý thư viện (Build Tool):** Maven
* **Bảo mật (Security):** Oauth2, JWT
* **Triển khai (Containerization):** Docker & Docker Compose, Redis(IDE BASIC), POSTGRES
* **Tài liệu API:** /e-commerce_backend api

## Cấu trúc thư mục (Project Structure)
Dự án được phân chia module rõ ràng để dễ dàng bảo trì và phát triển:

- `src/` : Chứa toàn bộ mã nguồn Java của ứng dụng. Nổi bật với các module nghiệp vụ như Quản lý sản phẩm (Product Management: thêm, sửa, xóa sản phẩm).
- `e-commerce_backend api/` : Chứa các file đặc tả API (ví dụ: `create.yml`, `productDetail.yml`) giúp Frontend hoặc các bên thứ 3 dễ dàng tích hợp.
- `pom.xml` : Cấu hình các dependencies và plugin của Maven.
- `docker-compose.yml` : File cấu hình để dựng nhanh môi trường chạy các dịch vụ phụ thuộc (Caching) bằng Docker.

## 🛠 Hướng dẫn cài đặt và khởi chạy (Getting Started)

### Điều kiện tiên quyết (Prerequisites)
Đảm bảo máy tính của bạn đã cài đặt sẵn các công cụ sau:
* Java Development Kit (JDK 17+)
* Apache Maven
* Docker và Docker Desktop (để chạy môi trường container)

### Các bước chạy ứng dụng dưới Local
**Bước 1: Clone dự án về máy**
```bash
git clone [https://github.com/ntai18/e-commerce_backend.git](https://github.com/ntai18/e-commerce_backend.git)
cd e-commerce_backend
```
**Bước 2: Khởi động các dịch vụ hạ tầng**
```bash
docker-compose up -d
```
**Bước 3: Build và chạy ứng dụng Spring Boot**
```bash
mvn clean install
```
continue
```bash
mvn spring-boot:run
```
* Hệ thống sẽ mặc định khởi chạy ở port 8080 (hoặc port bạn đã cấu hình trong application.properties/application.yml).
