// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: game.proto

package model;

public final class GameProtos {
  private GameProtos() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public interface GameOrBuilder extends
      // @@protoc_insertion_point(interface_extends:model.Game)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>required string id = 1;</code>
     */
    boolean hasId();
    /**
     * <code>required string id = 1;</code>
     */
    java.lang.String getId();
    /**
     * <code>required string id = 1;</code>
     */
    com.google.protobuf.ByteString
        getIdBytes();

    /**
     * <code>optional int32 minSize = 2;</code>
     */
    boolean hasMinSize();
    /**
     * <code>optional int32 minSize = 2;</code>
     */
    int getMinSize();

    /**
     * <code>optional int32 maxSize = 3;</code>
     */
    boolean hasMaxSize();
    /**
     * <code>optional int32 maxSize = 3;</code>
     */
    int getMaxSize();

    /**
     * <code>repeated string userIds = 4;</code>
     */
    java.util.List<java.lang.String>
        getUserIdsList();
    /**
     * <code>repeated string userIds = 4;</code>
     */
    int getUserIdsCount();
    /**
     * <code>repeated string userIds = 4;</code>
     */
    java.lang.String getUserIds(int index);
    /**
     * <code>repeated string userIds = 4;</code>
     */
    com.google.protobuf.ByteString
        getUserIdsBytes(int index);
  }
  /**
   * Protobuf type {@code model.Game}
   */
  public  static final class Game extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:model.Game)
      GameOrBuilder {
  private static final long serialVersionUID = 0L;
    // Use Game.newBuilder() to construct.
    private Game(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }
    private Game() {
      id_ = "";
      minSize_ = 0;
      maxSize_ = 0;
      userIds_ = com.google.protobuf.LazyStringArrayList.EMPTY;
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return this.unknownFields;
    }
    private Game(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      this();
      int mutable_bitField0_ = 0;
      com.google.protobuf.UnknownFieldSet.Builder unknownFields =
          com.google.protobuf.UnknownFieldSet.newBuilder();
      try {
        boolean done = false;
        while (!done) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              done = true;
              break;
            default: {
              if (!parseUnknownField(
                  input, unknownFields, extensionRegistry, tag)) {
                done = true;
              }
              break;
            }
            case 10: {
              com.google.protobuf.ByteString bs = input.readBytes();
              bitField0_ |= 0x00000001;
              id_ = bs;
              break;
            }
            case 16: {
              bitField0_ |= 0x00000002;
              minSize_ = input.readInt32();
              break;
            }
            case 24: {
              bitField0_ |= 0x00000004;
              maxSize_ = input.readInt32();
              break;
            }
            case 34: {
              com.google.protobuf.ByteString bs = input.readBytes();
              if (!((mutable_bitField0_ & 0x00000008) == 0x00000008)) {
                userIds_ = new com.google.protobuf.LazyStringArrayList();
                mutable_bitField0_ |= 0x00000008;
              }
              userIds_.add(bs);
              break;
            }
          }
        }
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessage(this);
      } catch (java.io.IOException e) {
        throw new com.google.protobuf.InvalidProtocolBufferException(
            e).setUnfinishedMessage(this);
      } finally {
        if (((mutable_bitField0_ & 0x00000008) == 0x00000008)) {
          userIds_ = userIds_.getUnmodifiableView();
        }
        this.unknownFields = unknownFields.build();
        makeExtensionsImmutable();
      }
    }
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return model.GameProtos.internal_static_model_Game_descriptor;
    }

    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return model.GameProtos.internal_static_model_Game_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              model.GameProtos.Game.class, model.GameProtos.Game.Builder.class);
    }

    private int bitField0_;
    public static final int ID_FIELD_NUMBER = 1;
    private volatile java.lang.Object id_;
    /**
     * <code>required string id = 1;</code>
     */
    public boolean hasId() {
      return ((bitField0_ & 0x00000001) == 0x00000001);
    }
    /**
     * <code>required string id = 1;</code>
     */
    public java.lang.String getId() {
      java.lang.Object ref = id_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        if (bs.isValidUtf8()) {
          id_ = s;
        }
        return s;
      }
    }
    /**
     * <code>required string id = 1;</code>
     */
    public com.google.protobuf.ByteString
        getIdBytes() {
      java.lang.Object ref = id_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        id_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    public static final int MINSIZE_FIELD_NUMBER = 2;
    private int minSize_;
    /**
     * <code>optional int32 minSize = 2;</code>
     */
    public boolean hasMinSize() {
      return ((bitField0_ & 0x00000002) == 0x00000002);
    }
    /**
     * <code>optional int32 minSize = 2;</code>
     */
    public int getMinSize() {
      return minSize_;
    }

    public static final int MAXSIZE_FIELD_NUMBER = 3;
    private int maxSize_;
    /**
     * <code>optional int32 maxSize = 3;</code>
     */
    public boolean hasMaxSize() {
      return ((bitField0_ & 0x00000004) == 0x00000004);
    }
    /**
     * <code>optional int32 maxSize = 3;</code>
     */
    public int getMaxSize() {
      return maxSize_;
    }

    public static final int USERIDS_FIELD_NUMBER = 4;
    private com.google.protobuf.LazyStringList userIds_;
    /**
     * <code>repeated string userIds = 4;</code>
     */
    public com.google.protobuf.ProtocolStringList
        getUserIdsList() {
      return userIds_;
    }
    /**
     * <code>repeated string userIds = 4;</code>
     */
    public int getUserIdsCount() {
      return userIds_.size();
    }
    /**
     * <code>repeated string userIds = 4;</code>
     */
    public java.lang.String getUserIds(int index) {
      return userIds_.get(index);
    }
    /**
     * <code>repeated string userIds = 4;</code>
     */
    public com.google.protobuf.ByteString
        getUserIdsBytes(int index) {
      return userIds_.getByteString(index);
    }

    private byte memoizedIsInitialized = -1;
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized == 1) return true;
      if (isInitialized == 0) return false;

      if (!hasId()) {
        memoizedIsInitialized = 0;
        return false;
      }
      memoizedIsInitialized = 1;
      return true;
    }

    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      if (((bitField0_ & 0x00000001) == 0x00000001)) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 1, id_);
      }
      if (((bitField0_ & 0x00000002) == 0x00000002)) {
        output.writeInt32(2, minSize_);
      }
      if (((bitField0_ & 0x00000004) == 0x00000004)) {
        output.writeInt32(3, maxSize_);
      }
      for (int i = 0; i < userIds_.size(); i++) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 4, userIds_.getRaw(i));
      }
      unknownFields.writeTo(output);
    }

    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (((bitField0_ & 0x00000001) == 0x00000001)) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(1, id_);
      }
      if (((bitField0_ & 0x00000002) == 0x00000002)) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt32Size(2, minSize_);
      }
      if (((bitField0_ & 0x00000004) == 0x00000004)) {
        size += com.google.protobuf.CodedOutputStream
          .computeInt32Size(3, maxSize_);
      }
      {
        int dataSize = 0;
        for (int i = 0; i < userIds_.size(); i++) {
          dataSize += computeStringSizeNoTag(userIds_.getRaw(i));
        }
        size += dataSize;
        size += 1 * getUserIdsList().size();
      }
      size += unknownFields.getSerializedSize();
      memoizedSize = size;
      return size;
    }

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this) {
       return true;
      }
      if (!(obj instanceof model.GameProtos.Game)) {
        return super.equals(obj);
      }
      model.GameProtos.Game other = (model.GameProtos.Game) obj;

      boolean result = true;
      result = result && (hasId() == other.hasId());
      if (hasId()) {
        result = result && getId()
            .equals(other.getId());
      }
      result = result && (hasMinSize() == other.hasMinSize());
      if (hasMinSize()) {
        result = result && (getMinSize()
            == other.getMinSize());
      }
      result = result && (hasMaxSize() == other.hasMaxSize());
      if (hasMaxSize()) {
        result = result && (getMaxSize()
            == other.getMaxSize());
      }
      result = result && getUserIdsList()
          .equals(other.getUserIdsList());
      result = result && unknownFields.equals(other.unknownFields);
      return result;
    }

    @java.lang.Override
    public int hashCode() {
      if (memoizedHashCode != 0) {
        return memoizedHashCode;
      }
      int hash = 41;
      hash = (19 * hash) + getDescriptor().hashCode();
      if (hasId()) {
        hash = (37 * hash) + ID_FIELD_NUMBER;
        hash = (53 * hash) + getId().hashCode();
      }
      if (hasMinSize()) {
        hash = (37 * hash) + MINSIZE_FIELD_NUMBER;
        hash = (53 * hash) + getMinSize();
      }
      if (hasMaxSize()) {
        hash = (37 * hash) + MAXSIZE_FIELD_NUMBER;
        hash = (53 * hash) + getMaxSize();
      }
      if (getUserIdsCount() > 0) {
        hash = (37 * hash) + USERIDS_FIELD_NUMBER;
        hash = (53 * hash) + getUserIdsList().hashCode();
      }
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static model.GameProtos.Game parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static model.GameProtos.Game parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static model.GameProtos.Game parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static model.GameProtos.Game parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static model.GameProtos.Game parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static model.GameProtos.Game parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static model.GameProtos.Game parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static model.GameProtos.Game parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }
    public static model.GameProtos.Game parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }
    public static model.GameProtos.Game parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static model.GameProtos.Game parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static model.GameProtos.Game parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }

    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder() {
      return DEFAULT_INSTANCE.toBuilder();
    }
    public static Builder newBuilder(model.GameProtos.Game prototype) {
      return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
    }
    public Builder toBuilder() {
      return this == DEFAULT_INSTANCE
          ? new Builder() : new Builder().mergeFrom(this);
    }

    @java.lang.Override
    protected Builder newBuilderForType(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    /**
     * Protobuf type {@code model.Game}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:model.Game)
        model.GameProtos.GameOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return model.GameProtos.internal_static_model_Game_descriptor;
      }

      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return model.GameProtos.internal_static_model_Game_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                model.GameProtos.Game.class, model.GameProtos.Game.Builder.class);
      }

      // Construct using model.GameProtos.Game.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }

      private Builder(
          com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitialization();
      }
      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessageV3
                .alwaysUseFieldBuilders) {
        }
      }
      public Builder clear() {
        super.clear();
        id_ = "";
        bitField0_ = (bitField0_ & ~0x00000001);
        minSize_ = 0;
        bitField0_ = (bitField0_ & ~0x00000002);
        maxSize_ = 0;
        bitField0_ = (bitField0_ & ~0x00000004);
        userIds_ = com.google.protobuf.LazyStringArrayList.EMPTY;
        bitField0_ = (bitField0_ & ~0x00000008);
        return this;
      }

      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return model.GameProtos.internal_static_model_Game_descriptor;
      }

      public model.GameProtos.Game getDefaultInstanceForType() {
        return model.GameProtos.Game.getDefaultInstance();
      }

      public model.GameProtos.Game build() {
        model.GameProtos.Game result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      public model.GameProtos.Game buildPartial() {
        model.GameProtos.Game result = new model.GameProtos.Game(this);
        int from_bitField0_ = bitField0_;
        int to_bitField0_ = 0;
        if (((from_bitField0_ & 0x00000001) == 0x00000001)) {
          to_bitField0_ |= 0x00000001;
        }
        result.id_ = id_;
        if (((from_bitField0_ & 0x00000002) == 0x00000002)) {
          to_bitField0_ |= 0x00000002;
        }
        result.minSize_ = minSize_;
        if (((from_bitField0_ & 0x00000004) == 0x00000004)) {
          to_bitField0_ |= 0x00000004;
        }
        result.maxSize_ = maxSize_;
        if (((bitField0_ & 0x00000008) == 0x00000008)) {
          userIds_ = userIds_.getUnmodifiableView();
          bitField0_ = (bitField0_ & ~0x00000008);
        }
        result.userIds_ = userIds_;
        result.bitField0_ = to_bitField0_;
        onBuilt();
        return result;
      }

      public Builder clone() {
        return (Builder) super.clone();
      }
      public Builder setField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          java.lang.Object value) {
        return (Builder) super.setField(field, value);
      }
      public Builder clearField(
          com.google.protobuf.Descriptors.FieldDescriptor field) {
        return (Builder) super.clearField(field);
      }
      public Builder clearOneof(
          com.google.protobuf.Descriptors.OneofDescriptor oneof) {
        return (Builder) super.clearOneof(oneof);
      }
      public Builder setRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          int index, java.lang.Object value) {
        return (Builder) super.setRepeatedField(field, index, value);
      }
      public Builder addRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          java.lang.Object value) {
        return (Builder) super.addRepeatedField(field, value);
      }
      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof model.GameProtos.Game) {
          return mergeFrom((model.GameProtos.Game)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(model.GameProtos.Game other) {
        if (other == model.GameProtos.Game.getDefaultInstance()) return this;
        if (other.hasId()) {
          bitField0_ |= 0x00000001;
          id_ = other.id_;
          onChanged();
        }
        if (other.hasMinSize()) {
          setMinSize(other.getMinSize());
        }
        if (other.hasMaxSize()) {
          setMaxSize(other.getMaxSize());
        }
        if (!other.userIds_.isEmpty()) {
          if (userIds_.isEmpty()) {
            userIds_ = other.userIds_;
            bitField0_ = (bitField0_ & ~0x00000008);
          } else {
            ensureUserIdsIsMutable();
            userIds_.addAll(other.userIds_);
          }
          onChanged();
        }
        this.mergeUnknownFields(other.unknownFields);
        onChanged();
        return this;
      }

      public final boolean isInitialized() {
        if (!hasId()) {
          return false;
        }
        return true;
      }

      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        model.GameProtos.Game parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (model.GameProtos.Game) e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }
      private int bitField0_;

      private java.lang.Object id_ = "";
      /**
       * <code>required string id = 1;</code>
       */
      public boolean hasId() {
        return ((bitField0_ & 0x00000001) == 0x00000001);
      }
      /**
       * <code>required string id = 1;</code>
       */
      public java.lang.String getId() {
        java.lang.Object ref = id_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          if (bs.isValidUtf8()) {
            id_ = s;
          }
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>required string id = 1;</code>
       */
      public com.google.protobuf.ByteString
          getIdBytes() {
        java.lang.Object ref = id_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (java.lang.String) ref);
          id_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>required string id = 1;</code>
       */
      public Builder setId(
          java.lang.String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000001;
        id_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>required string id = 1;</code>
       */
      public Builder clearId() {
        bitField0_ = (bitField0_ & ~0x00000001);
        id_ = getDefaultInstance().getId();
        onChanged();
        return this;
      }
      /**
       * <code>required string id = 1;</code>
       */
      public Builder setIdBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  bitField0_ |= 0x00000001;
        id_ = value;
        onChanged();
        return this;
      }

      private int minSize_ ;
      /**
       * <code>optional int32 minSize = 2;</code>
       */
      public boolean hasMinSize() {
        return ((bitField0_ & 0x00000002) == 0x00000002);
      }
      /**
       * <code>optional int32 minSize = 2;</code>
       */
      public int getMinSize() {
        return minSize_;
      }
      /**
       * <code>optional int32 minSize = 2;</code>
       */
      public Builder setMinSize(int value) {
        bitField0_ |= 0x00000002;
        minSize_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>optional int32 minSize = 2;</code>
       */
      public Builder clearMinSize() {
        bitField0_ = (bitField0_ & ~0x00000002);
        minSize_ = 0;
        onChanged();
        return this;
      }

      private int maxSize_ ;
      /**
       * <code>optional int32 maxSize = 3;</code>
       */
      public boolean hasMaxSize() {
        return ((bitField0_ & 0x00000004) == 0x00000004);
      }
      /**
       * <code>optional int32 maxSize = 3;</code>
       */
      public int getMaxSize() {
        return maxSize_;
      }
      /**
       * <code>optional int32 maxSize = 3;</code>
       */
      public Builder setMaxSize(int value) {
        bitField0_ |= 0x00000004;
        maxSize_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>optional int32 maxSize = 3;</code>
       */
      public Builder clearMaxSize() {
        bitField0_ = (bitField0_ & ~0x00000004);
        maxSize_ = 0;
        onChanged();
        return this;
      }

      private com.google.protobuf.LazyStringList userIds_ = com.google.protobuf.LazyStringArrayList.EMPTY;
      private void ensureUserIdsIsMutable() {
        if (!((bitField0_ & 0x00000008) == 0x00000008)) {
          userIds_ = new com.google.protobuf.LazyStringArrayList(userIds_);
          bitField0_ |= 0x00000008;
         }
      }
      /**
       * <code>repeated string userIds = 4;</code>
       */
      public com.google.protobuf.ProtocolStringList
          getUserIdsList() {
        return userIds_.getUnmodifiableView();
      }
      /**
       * <code>repeated string userIds = 4;</code>
       */
      public int getUserIdsCount() {
        return userIds_.size();
      }
      /**
       * <code>repeated string userIds = 4;</code>
       */
      public java.lang.String getUserIds(int index) {
        return userIds_.get(index);
      }
      /**
       * <code>repeated string userIds = 4;</code>
       */
      public com.google.protobuf.ByteString
          getUserIdsBytes(int index) {
        return userIds_.getByteString(index);
      }
      /**
       * <code>repeated string userIds = 4;</code>
       */
      public Builder setUserIds(
          int index, java.lang.String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  ensureUserIdsIsMutable();
        userIds_.set(index, value);
        onChanged();
        return this;
      }
      /**
       * <code>repeated string userIds = 4;</code>
       */
      public Builder addUserIds(
          java.lang.String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  ensureUserIdsIsMutable();
        userIds_.add(value);
        onChanged();
        return this;
      }
      /**
       * <code>repeated string userIds = 4;</code>
       */
      public Builder addAllUserIds(
          java.lang.Iterable<java.lang.String> values) {
        ensureUserIdsIsMutable();
        com.google.protobuf.AbstractMessageLite.Builder.addAll(
            values, userIds_);
        onChanged();
        return this;
      }
      /**
       * <code>repeated string userIds = 4;</code>
       */
      public Builder clearUserIds() {
        userIds_ = com.google.protobuf.LazyStringArrayList.EMPTY;
        bitField0_ = (bitField0_ & ~0x00000008);
        onChanged();
        return this;
      }
      /**
       * <code>repeated string userIds = 4;</code>
       */
      public Builder addUserIdsBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  ensureUserIdsIsMutable();
        userIds_.add(value);
        onChanged();
        return this;
      }
      public final Builder setUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.setUnknownFields(unknownFields);
      }

      public final Builder mergeUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.mergeUnknownFields(unknownFields);
      }


      // @@protoc_insertion_point(builder_scope:model.Game)
    }

    // @@protoc_insertion_point(class_scope:model.Game)
    private static final model.GameProtos.Game DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new model.GameProtos.Game();
    }

    public static model.GameProtos.Game getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    @java.lang.Deprecated public static final com.google.protobuf.Parser<Game>
        PARSER = new com.google.protobuf.AbstractParser<Game>() {
      public Game parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
          return new Game(input, extensionRegistry);
      }
    };

    public static com.google.protobuf.Parser<Game> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<Game> getParserForType() {
      return PARSER;
    }

    public model.GameProtos.Game getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_model_Game_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_model_Game_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\ngame.proto\022\005model\"E\n\004Game\022\n\n\002id\030\001 \002(\t\022" +
      "\017\n\007minSize\030\002 \001(\005\022\017\n\007maxSize\030\003 \001(\005\022\017\n\007use" +
      "rIds\030\004 \003(\tB\023\n\005modelB\nGameProtos"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
        new com.google.protobuf.Descriptors.FileDescriptor.    InternalDescriptorAssigner() {
          public com.google.protobuf.ExtensionRegistry assignDescriptors(
              com.google.protobuf.Descriptors.FileDescriptor root) {
            descriptor = root;
            return null;
          }
        };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
        }, assigner);
    internal_static_model_Game_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_model_Game_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_model_Game_descriptor,
        new java.lang.String[] { "Id", "MinSize", "MaxSize", "UserIds", });
  }

  // @@protoc_insertion_point(outer_class_scope)
}
