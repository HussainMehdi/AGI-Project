# Sample Apps for Assistant SDK Testing

This project contains 6 complete sample apps to test the Assistant SDK's NLP prompt capabilities:

1. **E-commerce App** - Amazon/Lazada-style shopping app
2. **Food Delivery App** - Food ordering and delivery
3. **Ride Hailing App** - Uber/Grab-style ride booking
4. **Calendar App** - Event management and scheduling
5. **Marketplace App** - Google Play Store-style app store
6. **LMS App** - Google Classroom-style learning management

## Structure

Each app follows the same structure:
- Main activity with product/feature listings
- Detail views for items
- Shopping cart/booking/reservation screens
- User profile screens
- NLP prompt integration for testing

## Setup

All apps use the same Assistant SDK configuration. Make sure Ollama is running on your host machine at `http://127.0.0.1:11434`.

## Running Apps

Each app can be run independently from Android Studio. Select the app module and run it on an emulator or device.

## Testing NLP Prompts

Each app includes NLP prompt functionality. Example prompts:

### E-commerce:
- "Add iPhone 15 to cart"
- "View product details for Samsung TV"
- "Search for laptops"

### Food Delivery:
- "Order pizza"
- "Add burger to cart"
- "View restaurant menu"

### Ride Hailing:
- "Book a ride to airport"
- "View available drivers"
- "Cancel my booking"

### Calendar:
- "Create meeting at 3pm"
- "View events for tomorrow"
- "Delete event"

### Marketplace:
- "Install WhatsApp"
- "Search for games"
- "View app details"

### LMS:
- "View assignments"
- "Submit homework"
- "Join course"

